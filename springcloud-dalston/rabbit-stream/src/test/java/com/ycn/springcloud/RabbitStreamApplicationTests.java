package com.ycn.springcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableBinding(value = {RabbitStreamApplicationTests.SinkSender.class})
public class RabbitStreamApplicationTests {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void contextLoads() {
        sinkSender.output().send(MessageBuilder.withPayload("{\"name\":\"ycn\", \"sex\":\"男\", \"age\":24}").build());
    }

    public interface SinkSender {
        String OUTPUT = "greetings";

        @Output(SinkSender.OUTPUT)
        MessageChannel output();
    }
}
