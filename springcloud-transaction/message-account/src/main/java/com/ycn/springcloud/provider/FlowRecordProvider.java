package com.ycn.springcloud.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ycn.springcloud.api.FlowRecordApi;
import com.ycn.springcloud.entity.FlowRecord;
import com.ycn.springcloud.service.IFlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.provider
 * @ClassName FlowRecordProvider
 * @Date 2018/8/3 22:19
 */
@RestController
public class FlowRecordProvider implements FlowRecordApi {

    @Autowired
    private IFlowRecordService flowRecordService;

    @Override
    public FlowRecord findOrderFlowRecord(@RequestParam("orderId") Long orderId) {
        List<FlowRecord> orders = flowRecordService.selectList(
                new EntityWrapper<FlowRecord>().eq("order_id", orderId));
        if (orders != null && !orders.isEmpty())
            return orders.get(0);
        return null;
    }
}
