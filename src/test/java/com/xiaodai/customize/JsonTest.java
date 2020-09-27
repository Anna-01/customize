package com.xiaodai.customize;

import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.controller.po.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class JsonTest {
    @Resource

    @Test
    void testJsonSeriaible() {
        Person person = new Person();
        person.setPName("pName");
    }
}
