package com.ycn.springcloud.config;

import com.ycn.springcloud.mq.ActiveMqMessageSender;
import com.ycn.springcloud.mq.MessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息配置
 *
 * @author ycn
 * @package com.ycn.springcloud.config
 * @ClassName MessageSenderConfig
 * @Date 2018/7/29 16:52
 */
@Configuration
public class MessageConfig {
    @Bean
    public MessageSender messageSender() {
        return new ActiveMqMessageSender();
    }

    /**
     * 消息发送次数和间隔
     * @return
     */
    @Bean(name = "sendTimeInterval")
    public Map<Integer, Integer> sendTimeInterval() {
        /**Integer 1--发送次数   Integer 2--发送间隔*/
        Map<Integer, Integer> notifyParam = new HashMap<>();
        notifyParam.put(1, 1);
        notifyParam.put(2, 3);
        notifyParam.put(3, 5);
        notifyParam.put(4, 15);
        notifyParam.put(5, 30);
        return notifyParam;
    }
}
