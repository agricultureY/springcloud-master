package com.ycn.springcloud.mq;

import com.ycn.springcloud.entity.ReliableMessage;

/**
 * mq消息队列发送者
 *
 * @author ycn
 * @package com.ycn.springcloud.mq
 * @ClassName MessageSender
 * @Date 2018/7/30 9:13
 */
public interface MessageSender {

    /**
     * 发送消息
     * @param message
     */
    void sendMessage(ReliableMessage message);
}
