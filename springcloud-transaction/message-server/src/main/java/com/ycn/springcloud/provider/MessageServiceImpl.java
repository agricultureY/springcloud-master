package com.ycn.springcloud.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ycn.springcloud.api.MessageServerApi;
import com.ycn.springcloud.config.properties.MessageProperties;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.enums.MessageEnum;
import com.ycn.springcloud.mq.MessageSender;
import com.ycn.springcloud.page.PageFactory;
import com.ycn.springcloud.page.PageQuery;
import com.ycn.springcloud.page.PageResult;
import com.ycn.springcloud.service.IReliableMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.provider
 * @ClassName MessageServiceImpl
 * @Date 2018/7/31 15:51
 */
@RestController
public class MessageServiceImpl implements MessageServerApi {

    @Autowired
    private IReliableMessageService reliableMessageService;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private MessageProperties messageProperties;

    /**
     * 消息参数检查
     * @param reliableMessage
     */
    private void checkEmptyMessage(ReliableMessage reliableMessage) {
        if(null == reliableMessage)
            throw new RuntimeException("请求参数错误");
        if(StringUtils.isEmpty(reliableMessage.getMessageId()))
            throw new RuntimeException("消息ID不能为空");
        if(StringUtils.isEmpty(reliableMessage.getMessageBody()))
            throw new RuntimeException("消息数据不能为空");
        if(StringUtils.isEmpty(reliableMessage.getConsumerQueue()))
            throw new RuntimeException("消息队列不能为空");
    }

    /**
     * 预存储消息
     */
    @Override
    public ReliableMessage preSaveMessage(@RequestBody ReliableMessage reliableMessage) {
        /**检查数据完整性*/
        this.checkEmptyMessage(reliableMessage);
        /**设置状态为待确认*/
        reliableMessage.setStatus(MessageEnum.WAIT_VERIFY.name());
        /**标记未死亡*/
        reliableMessage.setAlreadyDead(MessageEnum.N.name());
        reliableMessage.setMessageSendTimes(0);
        reliableMessage.setUpdateTime(new Date());
        reliableMessageService.insert(reliableMessage);
        return reliableMessage;
    }

    /**
     * 根据消息ID获取消息
     */
    @Override
    public ReliableMessage getMessageByMessageId(@RequestParam("messageId") String messageId) {
        if(StringUtils.isEmpty(messageId))
            throw new RuntimeException("请求参数错误");
        List<ReliableMessage> list = this.reliableMessageService.selectList(new EntityWrapper<ReliableMessage>()
                .eq("message_id", messageId));
        if(CollectionUtils.isEmpty(list))
            throw new RuntimeException("该消息不存在");
        if(list.size() > 1)
            throw new RuntimeException("该消息数据异常");
        return list.get(0);
    }

    /**
     * 分页获取待发送超时的数据
     */
    @Override
    public PageResult<ReliableMessage> listPagetWaitConfimTimeOutMessages(@RequestBody PageQuery pageParam) {
        Page<ReliableMessage> page = new PageFactory<ReliableMessage>().createPage(pageParam);
        Page<ReliableMessage> reliableMessagePage = this.reliableMessageService.selectPage(page, new EntityWrapper<ReliableMessage>()
                .lt("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date(Calendar.getInstance().getTimeInMillis() - (messageProperties.getCheckInterval() * 1000))))
                .and()
                .eq("status", MessageEnum.WAIT_VERIFY.name()));
        if (page != null) {
            return new PageResult<>(reliableMessagePage);
        } else {
            return new PageResult<>();
        }
    }

    /**
     * 分页获取发送中超时的数据
     */
    @Override

    public PageResult<ReliableMessage> listPageSendingTimeOutMessages(@RequestBody PageQuery pageParam) {
        Page<ReliableMessage> page = new PageFactory<ReliableMessage>().createPage(pageParam);
        Page<ReliableMessage> reliableMessagePage = this.reliableMessageService.selectPage(page, new EntityWrapper<ReliableMessage>()
                .lt("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date(Calendar.getInstance().getTimeInMillis() - (messageProperties.getCheckInterval() * 1000))))
                .and()
                .eq("status", MessageEnum.SENDING.name())
                .and()
                .eq("already_dead", MessageEnum.N.name()));
        if (page != null) {
            return new PageResult<>(reliableMessagePage);
        } else {
            return new PageResult<>();
        }
    }

    /**
     * 确认并发送消息
     */
    @Override
    public void confirmAndSendMessage(@RequestParam("messageId") String messageId) {
        ReliableMessage message = this.reliableMessageService.selectOne(new EntityWrapper<ReliableMessage>()
                .eq("message_id", messageId));
        if(null == message)
            throw new RuntimeException("该消息不存在");

        message.setStatus(MessageEnum.SENDING.name());
        message.setUpdateTime(new Date());
        reliableMessageService.updateById(message);

        /**发送消息*/
        messageSender.sendMessage(message);
    }

    /**
     * 存储并发送消息
     */
    @Override
    public void saveAndSendMessage(@RequestBody ReliableMessage reliableMessage) {
        /**检查消息完整性*/
        this.checkEmptyMessage(reliableMessage);

        reliableMessage.setStatus(MessageEnum.SENDING.name());
        reliableMessage.setAlreadyDead(MessageEnum.N.name());
        reliableMessage.setMessageSendTimes(0);
        reliableMessage.setUpdateTime(new Date());
        reliableMessageService.insert(reliableMessage);

        /**发送消息*/
        messageSender.sendMessage(reliableMessage);
    }

    /**
     * 直接发送消息
     */
    @Override
    public void directSendMessage(@RequestBody ReliableMessage reliableMessage) {
        /**检查消息完整性*/
        this.checkEmptyMessage(reliableMessage);
        /**发送消息*/
        messageSender.sendMessage(reliableMessage);
    }

    /**
     * 重发消息
     */
    @Override
    public void reSendMessage(@RequestBody ReliableMessage reliableMessage) {
        /**检查消息完整性*/
        this.checkEmptyMessage(reliableMessage);
        /**更新消息发送次数*/
        reliableMessage.setMessageSendTimes(reliableMessage.getMessageSendTimes() + 1);
        reliableMessage.setUpdateTime(new Date());
        reliableMessageService.updateById(reliableMessage);
        /**发送消息*/
        messageSender.sendMessage(reliableMessage);
    }

    /**
     * 根据messageId重发某条消息
     */
    @Override
    public void reSendMessageByMessageId(@RequestParam("messageId") String messageId) {
        /**通过消息ID获取消息*/
        ReliableMessage message = this.getMessageByMessageId(messageId);
        /**修改消息状态*/
        message.setMessageSendTimes(message.getMessageSendTimes() + 1);
        message.setUpdateTime(new Date());
        this.reliableMessageService.updateById(message);
        /**发送消息*/
        this.messageSender.sendMessage(message);
    }

    /**
     * 将消息标记为死亡消息
     */
    @Override
    public void setMessageToAreadlyDead(@RequestParam("messageId") String messageId) {
        /**通过消息ID获取消息*/
        ReliableMessage message = this.getMessageByMessageId(messageId);
        /**修改消息状态*/
        message.setAlreadyDead(MessageEnum.Y.name());
        message.setUpdateTime(new Date());
        this.reliableMessageService.updateById(message);
        /**发送消息*/
        this.messageSender.sendMessage(message);
    }

    /**
     * 根据消息ID删除消息
     */
    @Override
    public void deleteMessageByMessageId(@RequestParam("messageId") String messageId) {
        /**检验请求参数*/
        if(StringUtils.isEmpty(messageId))
            throw new RuntimeException("请求参数错误");
        this.reliableMessageService.delete(new EntityWrapper<ReliableMessage>()
                .eq("message_id", messageId));
    }

    /**
     * 根据业务id删除消息
     */
    @Override
    public void deleteMessageByBizId(@RequestParam("bizId") Long bizId) {
        /**检验请求参数*/
        if(StringUtils.isEmpty(bizId))
            throw new RuntimeException("请求参数错误");
        this.reliableMessageService.delete(new EntityWrapper<ReliableMessage>()
                .eq("biz_unique_id", bizId));

    }

    /**
     * 重发某个消息队列中的全部已死亡的消息.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reSendAllDeadMessageByQueueName(@RequestParam("queueName") String queueName) {
        if(StringUtils.isEmpty(queueName))
            throw new RuntimeException("请求参数错误");
        /**查询指定队列已死亡的消息*/
        List<ReliableMessage> msgs = this.reliableMessageService.selectList(new EntityWrapper<ReliableMessage>()
                .eq("consumer_queue", queueName)
                .eq("already_dead", MessageEnum.Y.name()));
        if(CollectionUtils.isEmpty(msgs))
            return;
        /**重新发送消息*/
        for (ReliableMessage msg : msgs) {
            msg.setUpdateTime(new Date());
            msg.setMessageSendTimes(msg.getMessageSendTimes() + 1);
            this.reliableMessageService.updateById(msg);
            this.messageSender.sendMessage(msg);
        }
    }
}
