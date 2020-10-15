package com.xiaodai.customize.service;

import com.xiaodai.customize.service.cache.CustomizeCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ljx
 */
@Service("commonService")
public class CommonService {
    @Resource
    private CustomizeCacheService customizeCacheService;

    /**
     * 处理用户信息

     * @return
     */
    public String dealUserInfo() {

    }

}
