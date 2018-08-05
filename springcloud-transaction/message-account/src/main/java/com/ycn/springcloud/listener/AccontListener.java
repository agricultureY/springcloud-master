package com.ycn.springcloud.listener;

import com.alibaba.fastjson.JSON;
import com.ycn.springcloud.dto.GoodsFlowParam;
import com.ycn.springcloud.service.IFlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *消息队列监听
 *
 * @author ycn
 * @package com.ycn.springcloud.listener
 * @ClassName AccontListener
 * @Date 2018/8/2 19:44
 */
@Component
public class AccontListener implements MessageListener {

    @Autowired
    private IFlowRecordService flowRecordService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String messageBody = ((TextMessage) message).getText();
                if(StringUtils.isEmpty(messageBody))
                    throw new RuntimeException("消息数据为空");
                GoodsFlowParam flow = JSON.parseObject(messageBody, GoodsFlowParam.class);
                flowRecordService.recordFlow(flow);
            } catch (JMSException e) {
                throw new RuntimeException("消息服务异常");
            }
        }else {
            throw new RuntimeException("消息格式错误");
        }
    }
}
