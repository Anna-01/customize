package com.xiaodai.customize.service.cache;

import com.xiaodai.customize.annotation.CustomizeCache;
import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.service.inner.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljx
 */
@Service
public class CustomizeCacheService {

    Logger logger = LoggerFactory.getLogger(CustomizeCacheService.class);
    @Resource
    private PersonService personService;

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

    /**
     * 异步保存数据
     *
     * @return
     */
    @Async
    public void saveUserInfo() {
        personService.addPerson(Men.builder()
                .age("11")
                .name("lijiaxing").build());
    }


    /**
     * 正常方法
     * @return
     */
    public void taskMethod() throws Exception{
        logger.info("当前线程={}", Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        Thread.sleep(3000);
        long endTime = System.currentTimeMillis();

        logger.info("完成时间耗时={}", endTime - startTime);
    }

    /**
     * 异步方法
     * @return
     */
    @Async
    public void asyncMethod() throws Exception {
        logger.info("当前线程={}", Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        //Thread.sleep(3000);
        long endTime = System.currentTimeMillis();

        logger.info("完成时间耗时={}", endTime - startTime);
    }
}
