package com.ycn.springcloud.annotation;

import com.ycn.springcloud.constant.TokenEnum;

import java.lang.annotation.*;

/**
 * 自定义接口幂等性验证注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LxToken {

    /**
     *
     * @return
     */
    TokenEnum type() default TokenEnum.TOKEN_API;
}
