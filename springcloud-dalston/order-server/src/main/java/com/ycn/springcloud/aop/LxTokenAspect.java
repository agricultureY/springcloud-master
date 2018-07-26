package com.ycn.springcloud.aop;

import com.ycn.springcloud.annotation.LxToken;
import com.ycn.springcloud.constant.TokenEnum;
import com.ycn.springcloud.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author ycn
 * @package com.ycn.springcloud.aop
 * @ClassName LxTokenAspect
 * @Date 2018/7/26 10:28
 */
@Aspect
@Component
public class LxTokenAspect {

    private final static Logger logger = LoggerFactory.getLogger(LxTokenAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.ycn.springcloud.annotation.LxToken)")
    public void checkPointcut() { }

    @ResponseBody
    @Around(value = "checkPointcut()")
    public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("拦截到了{}方法...", pjp.getSignature().getName());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(LxToken.class)) {
            //获取目标方法的@LxToken注解
            LxToken lxToken = targetMethod.getAnnotation(LxToken.class);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String token = "";
            if(TokenEnum.TOKEN_API.equals(lxToken.type()))
                token = request.getHeader("token");
            if (TokenEnum.TOKEN_FORM.equals(lxToken.type()))
                token = request.getParameter("token");
            if(!redisUtil.exists(token))
                return "请求失败";
            redisUtil.del(token);
        }
        return pjp.proceed();
    }
}
