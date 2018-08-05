package com.ycn.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息检查配置
 *
 * @author ycn
 * @package com.ycn.springcloud.config
 * @ClassName MessageCheckConfig
 * @Date 2018/7/31 10:15
 */
@Configuration
public class MessageCheckConfig {

    /**
     * 消息发送次数和间隔
     * @return
     */
    @Bean(name = "sendTimeInterval")
    public Map<Integer, Integer> sendTimeInterval() {
        /**Integer 1--发送次数   Integer 2--发送间隔(毫秒)*/
        Map<Integer, Integer> notifyParam = new HashMap<>();
        notifyParam.put(1, 1 * 60 * 1000);
        notifyParam.put(2, 3 * 60 * 1000);
        notifyParam.put(3, 5 * 60 * 1000);
        notifyParam.put(4, 15 * 60 * 1000);
        notifyParam.put(5, 30 * 60 * 1000);
        return notifyParam;
    }
}
