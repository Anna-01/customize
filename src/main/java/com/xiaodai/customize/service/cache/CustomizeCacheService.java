package com.xiaodai.customize.service.cache;

import com.xiaodai.customize.annotation.CustomizeCache;

/**
 * @author ljx
 */
public class CustomizeCacheService {

    @CustomizeCache
    public String getUserInfoById(Long id) {
        return null;
    }

}
