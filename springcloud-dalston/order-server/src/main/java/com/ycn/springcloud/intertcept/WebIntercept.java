package com.ycn.springcloud.intertcept;

import com.ycn.redistool.annotation.ControllerLimit;
import com.ycn.redistool.limit.RedisLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web拦截
 *
 * @author ycn
 * @package com.ycn.redistool.intercept
 * @ClassName ControllerAspect
 * @Date 2018/7/21 23:01
 */
@Component
public class WebIntercept extends WebMvcConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebIntercept.class);

    @Autowired
    private RedisLimit redisLimit;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**");//拦截所有请求
    }

    private class CustomInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                 Object handler) throws Exception {
            if (redisLimit == null) {
                throw new NullPointerException("redisLimit is null");
            }
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) handler;
                ControllerLimit annotation = method.getMethodAnnotation(ControllerLimit.class);
                if (annotation == null) {
                    return true;
                }
                boolean limit = redisLimit.limit();
                if (!limit) {
                    logger.warn("request has bean limited");
                    response.sendError(500, "request limited");
                    return false;
                }
            }
            return true;
        }
    }
}
