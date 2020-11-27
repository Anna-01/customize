package com.xiaodai.customize.controller.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.io.Serializable;

/**
 * men类
 * @author My
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Men implements Serializable {
    Person person;

    String name;

    String age;

    int high;

    boolean fix;

    long creatTime;

    int id;
}
