package com.ycn.springcloud;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.mapper.ReliableMessageMapper;
import com.ycn.springcloud.mq.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServerApplicationTests {

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    MessageSender messageSender;
    @Autowired
    ReliableMessageMapper mapper;

    @Test
    public void mapperTest() {
        List<ReliableMessage> list = mapper.selectList(new EntityWrapper<>());
        System.out.println(list.size());
    }

    @Test
    public void jmsTemplateTest() {
        /**设置消息总线队列*/
        jmsTemplate.setDefaultDestinationName("test.queue");
        /**设置ack确认为client方式*/
        jmsTemplate.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("测试发送消息");
            }
        });
    }

    @Test
    public void messageSenderTest() {
        ReliableMessage reliableMessage = new ReliableMessage("11111", "测试发送消息", "test.queue");
        messageSender.sendMessage(reliableMessage);
    }

}
