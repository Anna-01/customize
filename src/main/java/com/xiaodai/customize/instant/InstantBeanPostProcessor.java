package com.xiaodai.customize.instant;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听容器初始化事件
 * @author My
 */
@Component
public class InstantBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 容器初始换完成后执行此代码
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("容器初始化完成");
    }

    /**
     * 第二种方式使用@PostConstruct注解
     */
}
