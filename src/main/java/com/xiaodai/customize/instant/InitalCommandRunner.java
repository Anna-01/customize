package com.xiaodai.customize.instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * spring boot 容器加载后自动监听
 * @author
 */
@Component
public class InitalCommandRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("容器加载后自动监听");

    }
}
