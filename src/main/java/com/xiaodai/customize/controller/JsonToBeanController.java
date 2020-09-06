package com.xiaodai.customize.controller;

import com.xiaodai.customize.annotation.JsonAnnotation;
import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.service.JsonToBeanService;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author My
 */
@Controller
public class JsonToBeanController {

    public static volatile int mount = 0;
    public static HashMap map = new HashMap();
    public static Object object;

    @Resource
    public JsonToBeanService jsonToBeanService;

    public static final String jstring = "{\"name\":\"lijiaxing\",\"age\":\"11\", \"date\":\"2020-01-18-13\"}";
    /**
     *
     */
    @JsonAnnotation(toBean = Men.class)
    public void showJsonToBean() {
        jsonToBeanService.getJson(jstring);
    }

    /**
     * 容器初始化完成后执行某个方法
     */
    @PostConstruct
    public void InstantMethod() {
        System.out.println("容器初始化后通过注解标注执行");
    }


}

