package com.xiaodai.customize.service.cache;

import com.xiaodai.customize.annotation.CustomizeCache;
import com.xiaodai.customize.controller.po.Men;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljx
 */
@Service
public class CustomizeCacheService {

    Logger logger = LoggerFactory.getLogger(CustomizeCacheService.class);

    @CustomizeCache
    public String getUserInfoById(Long id) {
        logger.info("执行getUserInfoById 方法");
        return "返回结果";
    }

    @CustomizeCache
    public String getUserInfoByListId(List<Integer> list) {
        logger.info("getUserInfoByListId 方法");
        return "返回list结果";
    }

}
