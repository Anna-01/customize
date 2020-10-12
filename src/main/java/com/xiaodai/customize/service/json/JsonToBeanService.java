package com.xiaodai.customize.service.json;

import com.xiaodai.customize.annotation.JsonAnnotation;
import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author My
 */
@Service
public class JsonToBeanService {
    Logger logger = LoggerFactory.getLogger(JsonToBeanService.class);

    /**
     * jsonToBean
     */
    @JsonAnnotation(toBean = Men.class)
    public void getJson(String sourceJson) {

        Men result = (Men) ThreadLocalUtil.getThreadLocalValue();
        logger.info("获取转换结果={}", result.toString());
    }
}
