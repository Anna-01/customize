package com.xiaodai.customize.annotation;

import com.xiaodai.customize.controller.po.Men;

import java.lang.annotation.*;

/**
 * 解析json自定义注解
 * @author My
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonAnnotation {

    /**
     * 转换方法
     * @return
     */
    Class toBean() default Men.class;

    /**
     * 转换后方法的值
     */
    //Object getResult() default toBean().newInstance();
}
