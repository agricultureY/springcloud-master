package com.ycn.springcloud.service;

import com.baomidou.mybatisplus.service.IService;
import com.ycn.springcloud.dto.GoodsFlowParam;
import com.ycn.springcloud.entity.FlowRecord;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName IFlowRecordService
 * @Date 2018/8/2 20:56
 */
public interface IFlowRecordService extends IService<FlowRecord> {

    /**
     * 记录订单流水
     * @param flowParam
     */
    void recordFlow(GoodsFlowParam flowParam);
}
