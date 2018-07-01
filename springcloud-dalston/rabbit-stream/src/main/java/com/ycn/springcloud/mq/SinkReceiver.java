package com.ycn.springcloud.mq;

import com.ycn.springcloud.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 消息接收者
 *
 * 除了Sink之外，Spring Cloud Stream还默认实现了绑定output通道的Source接口，
 *  还有结合了Sink和Source的Processor接口，实际使用时我们也可以自己通过@Input和@Output注解来定义绑定消息通道的接口。
 *  当我们需要为@EnableBinding指定多个接口来绑定消息通道的时候，
 *  可以这样定义：@EnableBinding(value = {Sink.class, Source.class})。
 *
 * @author ycn
 * @package com.ycn.springcloud.mq
 * @ClassName SinkReceiver
 * @Date 2018/6/22 18:20
 */
@EnableBinding(Sink.class)//该注解用来指定一个或多个定义了@Input或@Output注解的接口，以此实现对消息通道（Channel）的绑定。
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    /**
     * 该注解主要定义在方法上，作用是将被修饰的方法注册为消息中间件上数据流的事件监听器，注解中的属性值对应了监听的消息通道名。
     * @param user
     */
    @StreamListener(Sink.INPUT)
    public void receive(User user) {
        logger.info("Received:" + user);
    }
}
