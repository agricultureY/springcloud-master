package com.ycn.springcloud.provider;

import com.ycn.springcloud.api.GoodsOrderApi;
import com.ycn.springcloud.entity.GoodsOrder;
import com.ycn.springcloud.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 * @package com.ycn.springcloud.provider
 * @ClassName GoodsOrderProvider
 * @Date 2018/8/2 15:36
 */
@RestController
public class GoodsOrderProvider implements GoodsOrderApi {

    @Autowired
    private IOrderService orderService;

    @Override
    public GoodsOrder findOrderById(@RequestParam("orderId") Long orderId) {
        if(StringUtils.isEmpty(orderId))
            return null;
        return orderService.selectById(orderId);
    }
}
