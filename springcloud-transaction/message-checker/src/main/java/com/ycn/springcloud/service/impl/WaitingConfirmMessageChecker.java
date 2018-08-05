package com.ycn.springcloud.service.impl;

import com.ycn.springcloud.consumer.GoodsOrderConsumer;
import com.ycn.springcloud.consumer.MessageServerConsumer;
import com.ycn.springcloud.entity.GoodsOrder;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.enums.OrderStatusEnum;
import com.ycn.springcloud.page.PageQuery;
import com.ycn.springcloud.page.PageResult;
import com.ycn.springcloud.service.MessageChecker;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 已超时待确认的消息检查
 *
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName WaitingConfirmMessageChecker
 * @Date 2018/7/31 11:19
 */
@Log4j
@Service
public class WaitingConfirmMessageChecker extends MessageChecker {

    @Autowired
    private MessageServerConsumer messageServerConsumer;
    @Autowired
    private GoodsOrderConsumer orderConsumer;

    @Override
    protected void processMessage(Map<String, ReliableMessage> messages) {
        for (Map.Entry<String, ReliableMessage> entry : messages.entrySet()) {
            ReliableMessage message = entry.getValue();
            try {
                Long orderId = message.getBizUniqueId();
                if (orderId == null) {
                    //如果订单失败，则删掉没用的消息
                    messageServerConsumer.deleteMessageByMessageId(message.getMessageId());
                } else {
                    GoodsOrder order = orderConsumer.findOrderById(orderId);
                    //如果订单成功，则确认消息并发送
                    if (order != null && OrderStatusEnum.SUCCESS.equals(order.getStatus())) {
                        messageServerConsumer.confirmAndSendMessage(message.getMessageId());
                    } else {
                        //如果订单失败，则删掉没用的消息
                        messageServerConsumer.deleteMessageByMessageId(message.getMessageId());
                    }
                }
            } catch (Exception e) {
                log.error("处理待确认消息异常！messageId=" + message.getMessageId(), e);
            }
        }
    }

    @Override
    protected PageResult<ReliableMessage> getPageResult(PageQuery pageQuery) {
        return messageServerConsumer.listPagetWaitConfimTimeOutMessages(pageQuery);
    }
}
