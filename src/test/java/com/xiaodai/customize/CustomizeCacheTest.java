package com.xiaodai.customize;

import com.xiaodai.customize.service.cache.CustomizeCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CustomizeCacheTest {
    @Resource
    private CustomizeCacheService customizeCacheService;

    @Test
    void testCache() {
        String result = customizeCacheService.getUserInfoById(111L);
        System.out.println("缓存结果:" + result);
    }

    @Test
    void testList() {
        List<Integer> list = new ArrayList<>(6);
        list.add(1);
        list.add(2);
        list.add(3);
        String result = customizeCacheService.getUserInfoByListId(list);
        System.out.println("缓存结果:" + result);
    }
}
