package com.xiaodai.customize.controller.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaodai.customize.aop.DateToLongSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class Men {
    String name;

    String age;

    int high;

    boolean fix;

    long creatTime;
}
