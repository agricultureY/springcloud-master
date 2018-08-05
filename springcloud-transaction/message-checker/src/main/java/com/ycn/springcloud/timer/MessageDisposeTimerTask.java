package com.ycn.springcloud.timer;

import com.ycn.springcloud.service.MessageChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息定时检查
 *
 * @author ycn
 * @package com.ycn.springcloud.timer
 * @ClassName MessageDisposeTimerTask
 * @Date 2018/7/31 10:22
 */
@Component
public class MessageDisposeTimerTask {

    @Autowired
    @Qualifier("sendingMessageChecker")
    private MessageChecker sendingMessageChecker;
    @Autowired
    @Qualifier("waitingConfirmMessageChecker")
    private MessageChecker waitingConfirmMessageChecker;

    /**
     * 定时检查已超时的待确认消息
     */
    /*@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行*/
    /*@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行*/
    /*@Scheduled(initialDelay=1000, fixedRate=10000) ：第一次延迟1秒后执行，之后按fixedRate的规则每10秒执行一次*/
    @Scheduled(fixedRate = 10000)
    public void checkWaitingConfirmTimeOutMessages() {
        waitingConfirmMessageChecker.checkMessage();
    }

    /**
     * 定时检查发送中但超时没有被成功消费确认的消息
     */
    @Scheduled(fixedRate = 10000)
    public void checkSendingConfirmTimeOutMessages() {
        sendingMessageChecker.checkMessage();
    }
}
