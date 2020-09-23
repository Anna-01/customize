package com.xiaodai.customize.annotation;

import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.xiaodai.customize.controller.po.Men;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 * @author My
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomizeCache {
    /**
     * 缓存的键
     * @return
     */
    String key() default "";

    /**
     * 是否开启缓存
     * @return
     */
    boolean condition() default true;

    /**
     * 缓存时间 分钟
     * 默认10分钟
     */
    int minute() default 10;

}
