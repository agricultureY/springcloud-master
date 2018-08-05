package com.ycn.springcloud.service.impl;

import com.ycn.springcloud.consumer.MessageServerConsumer;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.page.PageQuery;
import com.ycn.springcloud.page.PageResult;
import com.ycn.springcloud.service.MessageChecker;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

/**
 * 发送中因超时没有被成功消费确认的消息
 *
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName SendingMessageChecker
 * @Date 2018/7/31 11:14
 */
@Log4j
@Service
public class SendingMessageChecker extends MessageChecker {

    @Autowired
    private MessageServerConsumer messageServerConsumer;
    @Autowired
    @Qualifier("sendTimeInterval")
    private Map<Integer, Integer> notifyParam;

    @Override
    protected void processMessage(Map<String, ReliableMessage> messages) {
        /**单条消息处理*/
        for (Map.Entry<String, ReliableMessage> entry : messages.entrySet()) {
            ReliableMessage message = entry.getValue();
            try {
                /**判断发送次数*/
                int maxTimes = notifyParam.size();
                /**如果超过最大发送次数直接退出*/
                if (message.getMessageSendTimes() > maxTimes) {
                    /**将消息标记为死亡*/
                    messageServerConsumer.setMessageToAreadlyDead(message.getMessageId());
                    continue;
                }
                /**判断是否达到可以再次发送消息的时间间隔条件*/
                int reSendTimes = message.getMessageSendTimes(),
                        times = notifyParam.get(reSendTimes == 0? 1 : reSendTimes);
                long needTime = Calendar.getInstance().getTimeInMillis() - times,
                        hasTime = message.getUpdateTime().getTime();
                /**未到达再次发送消息的时间直接退出*/
                if(hasTime > needTime)
                    continue;
                /**重新发送*/
                messageServerConsumer.reSendMessage(message);
            } catch (Exception e) {
                log.error("处理[SENDING]消息ID为[" + message.getMessageId() + "]的消息异常!", e);
            }
        }
    }

    @Override
    protected PageResult<ReliableMessage> getPageResult(PageQuery pageQuery) {
        return messageServerConsumer.listPageSendingTimeOutMessages(pageQuery);
    }
}
