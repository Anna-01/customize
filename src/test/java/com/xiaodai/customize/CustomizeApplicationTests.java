package com.xiaodai.customize;

import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.service.JsonToBeanService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
class CustomizeApplicationTests {
    Logger logger = LoggerFactory.getLogger(CustomizeApplicationTests.class);
    public static Date date = new Date();
    public static final String jstring = "{\"name\":\"lijiaxing\",\"age\":\"11\", \"creatTime\":\"" + date + "\"}";

    @Resource
    public JsonToBeanService jsonToBeanService;


    @Test
    void contextLoads() {
        Date date =new Date();
        date.toString();
    }

    /**
     * 不会出现 多次 可能是没启动另一个容器
     */
    @Test
    void testJson() {
        logger.info("多线程解析json开始");
        //jsonToBeanService.getJson(jstring);
    }
    @Test
    void testMapSize() {
        HashMap map = new HashMap(16);
        map.put("1", 1);
        System.out.println(map.size());
    }

    /**
     * 容器启动后就执行
     */
    @Test
    //@PostConstruct
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
            jsonToBeanService.getJson(jstring);
        }
    }

    @Test
    void testMen () {
        System.out.println(jstring);
        Men men = new Men();
        men.setName("ljx");
        System.out.println(men.toString());
    }

}
