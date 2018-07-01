package com.ycn.springcloud.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author ycn
 * @package com.ycn.springcloud.mq
 * @ClassName SinkSender
 * @Date 2018/6/27 19:55
 */
@EnableBinding(value = {Source.class})
public class SinkSender {
    private static Logger logger = LoggerFactory.getLogger(SinkSender.class);

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "2000"))//间隔2S发一次
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage<>("{\"name\":\"ycn\", \"sex\":\"男\", \"age\":24}");
    }
}
