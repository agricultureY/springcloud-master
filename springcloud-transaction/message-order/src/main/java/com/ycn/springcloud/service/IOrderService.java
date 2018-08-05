package com.ycn.springcloud.service;

import com.baomidou.mybatisplus.service.IService;
import com.ycn.springcloud.entity.GoodsOrder;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName IOrderService
 * @Date 2018/8/1 22:28
 */
public interface IOrderService extends IService<GoodsOrder> {

    /**
     * 下单
     * @return
     */
    Long makeOrder();

    /**
     * 完成订单
     */
    void finishOrder(String orderId);

}
