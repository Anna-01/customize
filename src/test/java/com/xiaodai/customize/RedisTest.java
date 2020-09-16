package com.xiaodai.customize;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.relational.core.sql.In;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    public static Logger logger = LoggerFactory.getLogger(RedisTest.class);
    public final String CE_SHI_KEY = "redis_key";
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void  addCache() {
        redisTemplate.opsForValue().set(CE_SHI_KEY, "测试0");
        Object result =  redisTemplate.opsForValue().get("key");
        System.out.println("结果" + result.toString());
        logger.info("输出结果日志测试");
        logger.info("输出结果测试 result={}", result);
    }
}
