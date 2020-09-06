package com.xiaodai.customize;

import com.xiaodai.customize.service.JsonToBeanService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
class CustomizeApplicationTests {

    public static final String jstring = "{\"name\":\"lijiaxing\",\"age\":\"11\", \"date\":\"2020-01-18-13\"}";

    @Resource
    public JsonToBeanService jsonToBeanService;

    @Test
    void contextLoads() {
        Date date =new Date();
        date.toString();
    }

    @Test
    void testJson() {
        jsonToBeanService.getJson(jstring);
    }
    @Test
    void testMapSize() {
        HashMap map = new HashMap(16);
        map.put("1", 1);
        System.out.println(map.size());
    }

    @Test
    @PostConstruct
    void testChange() {
        //顺序多线程
        new ThreadAnotion(jsonToBeanService).start();
        new ThreadAnotion(jsonToBeanService).start();
        new ThreadAnotion(jsonToBeanService).start();
    }

    private static class ThreadAnotion extends Thread {
        private JsonToBeanService jsonToBeanService;

        public ThreadAnotion(JsonToBeanService showService) {
            this.jsonToBeanService = showService;
        }

        @Override
        public void run() {
            jsonToBeanService.getJson("{\"name\":\"lijiaxing\",\"age\":\"null\"}");
        }
    }

}
