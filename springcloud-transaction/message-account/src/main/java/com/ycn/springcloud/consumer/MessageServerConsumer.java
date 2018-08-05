package com.ycn.springcloud.consumer;

import com.ycn.springcloud.api.MessageServerApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 消息服务消费者
 *
 * @author ycn
 * @package com.ycn.springcloud.consumer
 * @ClassName MessageServerConsumer
 * @Date 2018/7/31 10:25
 */
@FeignClient(value = "message-server")
public interface MessageServerConsumer extends MessageServerApi {

}
