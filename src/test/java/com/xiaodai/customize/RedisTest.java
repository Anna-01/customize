package com.xiaodai.customize;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    public static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void  addCache() {
        redisTemplate.opsForValue().set("CE_SHI", "测试value");
        String s= (String) redisTemplate.opsForValue().get("CE_SHI");
        System.out.println("结果" + s);
        logger.info("输出结果日志测试");
        logger.info("输出结果测试 s={}", s);
    }
}
