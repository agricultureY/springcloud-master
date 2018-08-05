package com.ycn.springcloud.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.ycn.springcloud.consumer.MessageServerConsumer;
import com.ycn.springcloud.entity.GoodsOrder;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.enums.MessageEnum;
import com.ycn.springcloud.enums.OrderStatusEnum;
import com.ycn.springcloud.mapper.OrderMapper;
import com.ycn.springcloud.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName OrderServiceImpl
 * @Date 2018/8/1 22:32
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, GoodsOrder> implements IOrderService {

    @Autowired
    MessageServerConsumer messageServerConsumer;

    @Override
    public Long makeOrder() {
        /**创建预发送消息*/
        GoodsOrder order = createOrder();
        /**下单*/
        this.insert(order);
        /**返回订单号*/
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishOrder(String orderId) {
        if(StringUtils.isEmpty(orderId))
            throw new RuntimeException("请求参数异常");
        GoodsOrder order = this.selectById(orderId);
        if(null == order)
            throw new RuntimeException("该订单不存在");
        if(OrderStatusEnum.SUCCESS.getStatus().equals(order.getStatus()))
            return;
        /**创建预发送消息*/
        ReliableMessage message = createMessage(order);
        /**预发送消息*/
        messageServerConsumer.preSaveMessage(message);
        /**更新订单为成功状态(百分之50几率失败，模拟错误数据)（此处错误已添加到消息表的数据会被message-checker轮询时删除掉）*/
        updateToSuccess(order);
        /**确认消息*/
        messageServerConsumer.confirmAndSendMessage(message.getMessageId());
    }

    /**
     * 创建消息通知
     * @param order
     * @return
     */
    private ReliableMessage createMessage(GoodsOrder order) {
        String messageId = IdWorker.getIdStr(),
                messageBody = JSON.toJSONString(order),
                queue = MessageEnum.MAKE_ORDER.getDesc();
        ReliableMessage msg = new ReliableMessage(messageId, messageBody, queue);
        msg.setBizUniqueId(order.getId());
        return msg;
    }

    /**
     * 创建订单
     * @return
     */
    private GoodsOrder createOrder() {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsName("商品" + RandomUtil.randomNumbers(4));
        goodsOrder.setCount(Integer.valueOf(RandomUtil.randomNumbers(1)));
        goodsOrder.setCreateTime(new Date());
        goodsOrder.setSum(new BigDecimal(RandomUtil.randomDouble(10.0, 50.0)).setScale(2, RoundingMode.HALF_UP));
        goodsOrder.setId(IdWorker.getId());
        goodsOrder.setUserId(IdWorker.getId());
        goodsOrder.setStatus(OrderStatusEnum.NOT_SUCCESS.getStatus());  //未完成的订单
        return goodsOrder;
    }

    /**
     * 更新订单状态  随机自定义异常
     * @param order
     */
    private void updateToSuccess(GoodsOrder order) {
        order.setStatus(OrderStatusEnum.SUCCESS.getStatus());
        this.updateById(order);
        int random = RandomUtil.randomInt(100);
        if(random > 50)
            return;
        else
            throw new RuntimeException("订单系统内部异常");
    }
}
