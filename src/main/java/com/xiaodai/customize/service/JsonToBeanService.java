package com.xiaodai.customize.service;

import com.xiaodai.customize.annotation.JsonAnnotation;
import com.xiaodai.customize.controller.po.Men;
import org.springframework.stereotype.Service;

/**
 * @author My
 */
@Service
public class JsonToBeanService {

    /**
     * jsonToBean
     */
    @JsonAnnotation(toBean = Men.class)
    public void getJson(String sourceJson) {
       //System.out.println("getJson输出" + sourceJson);
    }
}
