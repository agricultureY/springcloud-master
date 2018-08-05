package com.ycn.springcloud.controller;

import com.ycn.springcloud.entity.GoodsOrder;
import com.ycn.springcloud.service.IOrderService;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 *
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName OrderController
 * @Date 2018/8/2 15:46
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 下订单
     */
    @RequestMapping("/place")
    public String place() {
        Long orderId = orderService.makeOrder();
        return "order_id = " + orderId;
    }


    /**
     * 完成订单
     */
    @RequestMapping("/finish")
    public String finish(@RequestParam("orderId") String orderId) {
        orderService.finishOrder(orderId);
        return "success";
    }
}
