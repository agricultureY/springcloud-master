package com.ycn.springcloud.consumer;

import com.ycn.springcloud.api.GoodsOrderApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author ycn
 * @package com.ycn.springcloud.consumer
 * @ClassName GoodsOrderConsumer
 * @Date 2018/8/4 0:22
 */
@FeignClient("message-order")
public interface GoodsOrderConsumer extends GoodsOrderApi {
}
