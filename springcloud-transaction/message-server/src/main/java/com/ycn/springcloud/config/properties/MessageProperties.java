package com.ycn.springcloud.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.config.properties
 * @ClassName MessageProperties
 * @Date 2018/7/30 10:16
 */
@Component
@ConfigurationProperties(prefix = "message")
public class MessageProperties {

    /**
     * 消息检查的时间段
     */
    private Integer checkInterval;

    public Integer getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(Integer checkInterval) {
        this.checkInterval = checkInterval;
    }
}
