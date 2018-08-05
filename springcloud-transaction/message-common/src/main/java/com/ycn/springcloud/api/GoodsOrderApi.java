package com.ycn.springcloud.api;

import com.ycn.springcloud.entity.GoodsOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品订单API
 *
 * @author ycn
 * @package com.ycn.springcloud.api
 * @ClassName GoodsOrderApi
 * @Date 2018/8/1 21:49
 */
@RequestMapping("/order")
public interface GoodsOrderApi {

    /**
     * 根据订单ID查询订单信息
     * @param orderId
     * @return
     */
    @RequestMapping("/findOrderById")
    GoodsOrder findOrderById(@RequestParam("orderId") Long orderId);
}
