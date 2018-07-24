package com.ycn.springcloud.intertcept;

import com.ycn.redistool.annotation.SpringControllerLimit;
import com.ycn.redistool.limit.RedisLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ycn
 * @package com.ycn.redistool.intercept
 * @ClassName SpringMVCIntercept
 * @Date 2018/7/21 23:09
 */
@Component
public class SpringMVCIntercept extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SpringMVCIntercept.class);

    @Autowired
    private RedisLimit redisLimit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (redisLimit == null) {
            throw new NullPointerException("redisLimit is null");
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            SpringControllerLimit annotation = method.getMethodAnnotation(SpringControllerLimit.class);
            if (annotation == null) {
                //skip
                return true;
            }
            boolean limit = redisLimit.limit();
            if (!limit) {
                logger.warn(annotation.errorMsg());
                response.sendError(annotation.errorCode(), annotation.errorMsg());
                return false;
            }

        }
        return true;
    }
}
