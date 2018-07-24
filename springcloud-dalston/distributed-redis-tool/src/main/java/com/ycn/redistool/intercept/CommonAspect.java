package com.ycn.redistool.intercept;

import com.ycn.redistool.limit.RedisLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * commonLimit注解切面注入
 *
 * @author ycn
 * @package com.ycn.redistool.intercept
 * @ClassName CommonAscept
 * @Date 2018/7/21 22:46
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CommonAspect {
    private static Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Autowired
    private RedisLimit redisLimit ;

    @Pointcut("@annotation(com.ycn.redistool.annotation.CommonLimit)")
    private void check(){}

    @Before("check()")
    public void before(JoinPoint joinPoint) throws Exception {
        if (redisLimit == null) {
            throw new NullPointerException("redisLimit is null");
        }
        boolean limit = redisLimit.limit();
        if (!limit) {
            logger.warn("request has bean limited");
            throw new RuntimeException("request has bean limited") ;
        }
    }
}
