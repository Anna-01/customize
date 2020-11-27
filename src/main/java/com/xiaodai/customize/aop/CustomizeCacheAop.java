package com.xiaodai.customize.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.annotation.CustomizeCache;
import com.xiaodai.customize.base.LockInfo;
import com.xiaodai.customize.safe.SafeLock;
import jdk.nashorn.internal.ir.Block;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 执行顺序，@Around -  join.processed  -  @before - 方法  -  @After - join.processed后内容 - @AfterReturning-
 * 自定义缓存 注解 实现类
 *
 * @author My
 */
@Aspect
@Component
public class CustomizeCacheAop {

    Logger logger = LoggerFactory.getLogger(CustomizeCacheAop.class);

    private final Lock lock = new ReentrantLock();

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 实现思路
     * 1.before 判断 缓存中有没有没有就去执行 加入缓存 、、  应该用@around注解
     *
     * @param point
     */
    @Around(value = "@annotation(cache)")
    private Object cacheMethod(ProceedingJoinPoint point, CustomizeCache cache) {
        logger.info("开始执行缓存注解方法------");
        Object result = null;
        lock.lock();
        try {
            String key = null;
            //获取方法参数
            Object[] args = point.getArgs();
            key = JSON.toJSONString(args);

            //todo 线程安全为什么要锁

            //todo aop 多线程
            // todo 异步方法执行

            logger.info(Thread.currentThread().getName() + "线程获取锁执行");


            String value = stringRedisTemplate.opsForValue().get(key);
            logger.info("获取缓存结果key={}, value={}", key, value);

            if (!StringUtils.isEmpty(value)) {
                result = isJSONArray(value, point);
            } else {
                //执行方法获取返回值
                Object object = point.proceed(args);
                if (Objects.isNull(object)) {
                    return null;
                }

                //  缓存中没有 写入缓存 并返回结果
                logger.info("缓存加入 key={},value={}", key, JSON.toJSONString(object));
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(object));
                result = JSON.toJSONString(object);
            }
            return result;
        } catch (Throwable e) {
            logger.error("自定义缓存实现异常", e);
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 判断是是否是集合类型
     *
     * @param value
     * @param pjp
     * @return
     */
    private Object isJSONArray(String value, ProceedingJoinPoint pjp) {
        Object obj = JSONObject.parse(value);
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取方法返回值的类型
        Type type = signature.getMethod().getGenericReturnType();
        try {
            //<> 情况a
            if (obj instanceof JSONArray) {
                //a,b  a是否是b的子类   是否是参数化类型 <>符号的变量是参数化类型 类似于List<>
                if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                    Type[] varArray = ((ParameterizedType) type).getActualTypeArguments();
                    int length = varArray.length;
                    byte var = 0;
                    if (var < length) {
                        //获取<>中类型
                        Type resultType = varArray[var];
                        //将结果转化为这种类型
                        return JSONArray.parseArray(value, (Class) resultType);
                    }
                }
                return null;
            } else {
                //装载类
                return JSON.parseObject(value, Class.forName(type.getTypeName()));
            }
        } catch (Exception e) {
            logger.error("判断是否为集合异常", e);
            return null;
        }
    }
}
