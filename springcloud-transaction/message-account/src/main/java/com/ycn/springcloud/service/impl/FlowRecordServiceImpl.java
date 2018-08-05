package com.ycn.springcloud.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ycn.springcloud.consumer.MessageServerConsumer;
import com.ycn.springcloud.dto.GoodsFlowParam;
import com.ycn.springcloud.entity.FlowRecord;
import com.ycn.springcloud.mapper.FlowRecordMapper;
import com.ycn.springcloud.service.IFlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName FlowRecordServiceImpl
 * @Date 2018/8/2 20:59
 */
@Service
public class FlowRecordServiceImpl extends ServiceImpl<FlowRecordMapper, FlowRecord> implements IFlowRecordService {

    @Autowired
    private MessageServerConsumer messageConsumer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordFlow(GoodsFlowParam flowParam) {
        if(null == flowParam)
            throw new RuntimeException("请求参数错误");
        if(StringUtils.isEmpty(flowParam.getUserId()) || StringUtils.isEmpty(flowParam.getGoodsName()) || null == flowParam.getSum())
            throw new RuntimeException("请求参数错误");
        /**接口幂等性判断  已存在该订单流水信息  直接返回*/
        List<FlowRecord> flowList = this.selectList(new EntityWrapper<FlowRecord>().eq("order_id", flowParam.getId()));
        if(null != flowList && !flowList.isEmpty())
            return;

        /**创建订单流水信息*/
        FlowRecord flow = new FlowRecord();
        flow.setUserId(flowParam.getUserId());
        flow.setSum(flowParam.getSum());
        flow.setOrderId(flowParam.getId());
        flow.setName(flowParam.getGoodsName());
        flow.setCreateTime(new Date());
        this.insert(flow);

        /**测试可靠消息机制（结果百分之50几率成功）(如果此处有异常，会被roses-message-checker轮询处理，如果此处连续出现错误6次则可以通过查看消息表，人工干预*/
        if(RandomUtil.randomInt(100) > 50)
            throw new RuntimeException("自定义异常，验证可靠消息!");

        /**插入成功后删除消息  防止消息被重复消费*/
        messageConsumer.deleteMessageByBizId(flow.getOrderId());
    }
}
