package com.xiaodai.customize.instant;

import com.xiaodai.customize.service.json.JsonToBeanService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * spring web项目下，可能会造成二次执行，因为此时系统会存在两个容器
 * 一个是spring容器本身的root application context  另一个是serverlet context 容器 （为spring的子容器）
 * 可用if(event.getApplicationContext().getParent()==null) 条件规避
 * 监听容器初始化事件
 * @author My
 */
//@Component
public class InstantBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    public JsonToBeanService jsonToBeanService;
    /**
     * 创建Bean->Bean的属性注入->Bean初始化->Bean销毁
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
