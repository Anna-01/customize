package com.xiaodai.customize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author My
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CustomizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomizeApplication.class, args);
    }

}
