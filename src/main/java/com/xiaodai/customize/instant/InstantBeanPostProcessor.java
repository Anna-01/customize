package com.xiaodai.customize.instant;

import com.xiaodai.customize.service.JsonToBeanService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听容器初始化事件
 * @author My
 */
//@Component
public class InstantBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    public JsonToBeanService jsonToBeanService;
    /**
     * 容器初始换完成后执行此代码
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //启动一次
        if(event.getApplicationContext().getParent() == null) {
            testChange();
        }
        System.out.println("容器初始化完成");
    }

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
            jsonToBeanService.getJson("{\"name\":\"lijiaxing\",\"age\":\"11\"}");
        }
    }

    /**
     * 第二种方式使用@PostConstruct注解
     */
}
