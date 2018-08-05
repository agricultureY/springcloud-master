package com.ycn.springcloud.mq;

import com.ycn.springcloud.entity.ReliableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author ycn
 * @package com.ycn.springcloud.mq
 * @ClassName ActiveMqMessageSender
 * @Date 2018/7/30 9:12
 */
public class ActiveMqMessageSender implements MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(ReliableMessage message) {
        /**验证消息格式*/
        if(message == null ||
                StringUtils.isEmpty(message.getMessageBody()) ||
                StringUtils.isEmpty(message.getConsumerQueue())
                )
            throw new RuntimeException("消息格式错误");

        /**设置消息总线队列*/
        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        /**设置ack确认为client方式*/
        jmsTemplate.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
        /**发送消息*/
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message.getMessageBody());
            }
        });
    }
}
