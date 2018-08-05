package com.ycn.springcloud.config;

import com.ycn.springcloud.enums.MessageEnum;
import com.ycn.springcloud.listener.AccontListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

/**
 * 消息队列监听配置
 *
 * @author ycn
 * @package com.ycn.springcloud.config
 * @ClassName QueueListenerConfig
 * @Date 2018/8/2 19:43
 */
@Configuration
public class QueueListenerConfig {

    @Autowired
    private AccontListener accontListener;

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        DefaultMessageListenerContainer factory = new DefaultMessageListenerContainer();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationName(MessageEnum.MAKE_ORDER.getDesc());
        factory.setMessageListener(accontListener);
        return factory;
    }
}
