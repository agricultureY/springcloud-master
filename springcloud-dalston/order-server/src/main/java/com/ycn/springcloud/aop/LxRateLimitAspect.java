package com.ycn.springcloud.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.ycn.springcloud.annotation.LxRateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 环绕通知自定义限流注解
 *
 * @author ycn
 * @package com.ycn.springcloud.aop
 * @ClassName LxRateLimitAspect
 * @Date 2018/7/24 10:37
 */
@Aspect
@Component
public class LxRateLimitAspect {

    private final static Logger logger = LoggerFactory.getLogger(LxRateLimitAspect.class);

    private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    /**
     * 定义切点
     * 1、通过扫包切入
     * 2、带有指定注解切入
     */
//    @Pointcut("execution(public * com.ycn.springcloud.*.*(..))")
    @Pointcut("@annotation(com.ycn.springcloud.annotation.LxRateLimit)")
    public void checkPointcut() { }

    @ResponseBody
    @Around(value = "checkPointcut()")
    public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("拦截到了{}方法...", pjp.getSignature().getName());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(LxRateLimit.class)) {
            //获取目标方法的@LxRateLimit注解
            LxRateLimit lxRateLimit = targetMethod.getAnnotation(LxRateLimit.class);
            rateLimiter.setRate(lxRateLimit.perSecond());
            if (!rateLimiter.tryAcquire(lxRateLimit.timeOut(), lxRateLimit.timeOutUnit()))
                return "服务器繁忙，请稍后再试!";
        }
        return pjp.proceed();
    }
}
