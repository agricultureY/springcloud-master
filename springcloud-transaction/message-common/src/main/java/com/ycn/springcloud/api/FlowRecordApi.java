package com.ycn.springcloud.api;

import com.ycn.springcloud.entity.FlowRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 流水对外接口
 *
 * @author ycn
 * @package com.ycn.springcloud.api
 * @ClassName FlowRecordApi
 * @Date 2018/8/1 22:02
 */
@RequestMapping("/flowRecord")
public interface FlowRecordApi {

    /**
     * 根据订单ID查询流水
     * @param orderId
     * @return
     */
    @RequestMapping("/preSaveMessage")
    FlowRecord findOrderFlowRecord(@RequestParam("orderId") Long orderId);
}
