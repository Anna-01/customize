package com.xiaodai.customize.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 执行顺序，@Around -  join.processed  -  @before - 方法  -  @After - join.processed后内容 - @AfterReturning-
 * 自定义缓存 注解 实现类
 * @author My
 */
@Aspect
public class CustomizeCacheAop {

    /**
     * 切点
     */
    @Pointcut("@annotation(com.xiaodai.customize.annotation.CustomizeCache)")
    public void pointCut() {

    }

    /**
     * 实现思路
     * 1.before 判断 缓存中有没有没有就去执行 加入缓存 、、  应该用@around注解
     * @param point
     */
    @Before(value = "pointCut()")
    public void beforeMethod(ProceedingJoinPoint point) {
        //获取方法参数
        Object[] args = point.getArgs();


    }




}
