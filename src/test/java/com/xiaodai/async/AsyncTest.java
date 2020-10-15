package com.xiaodai.async;

import com.xiaodai.customize.CustomizeApplication;
import com.xiaodai.customize.annotation.CustomizeCache;
import com.xiaodai.customize.service.cache.CustomizeCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;


@SpringBootTest(classes = CustomizeApplication.class)
public class AsyncTest {
    @Resource
    private CustomizeCacheService customizeCacheService;

    @Test
    public void testNormal() {

        try {
            customizeCacheService.taskMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsync() throws Exception {
            System.out.println(Thread.currentThread().getName());
            customizeCacheService.asyncMethod();
    }
}
