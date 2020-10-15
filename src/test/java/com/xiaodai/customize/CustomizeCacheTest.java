package com.xiaodai.customize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.annotation.CustomizeCache;
import com.xiaodai.customize.service.cache.CustomizeCacheService;
import com.xiaodai.customize.service.json.JsonToBeanService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class CustomizeCacheTest {
    //并发数
    private static final int threadNum = 30;
    //倒计时数 发令枪 用于制造线程的并发执行
    private static CountDownLatch cdl = new CountDownLatch(threadNum);

    @Resource
    private CustomizeCacheService customizeCacheService;


    @Test
    void testCache() {
        String result = customizeCacheService.getUserInfoById(111L);
        System.out.println("缓存结果:" + result);
    }

    @Test
    void testList() {
        List<Integer> list = new ArrayList<>(6);
        list.add(1);
        list.add(2);
        list.add(3);
        String result = customizeCacheService.getUserInfoByListId(list);
        System.out.println("缓存结果:" + result);
    }

    /**
     * 模拟并发
     */
    @Test
    //@PostConstruct
    public void testConcurrent() {
        for(int i = 0; i< threadNum; i++) {
            //new多个子线程
            new Thread(new CustomizeCacheTest.ThreadClass(customizeCacheService)).start();
            //计数器-1
            cdl.countDown();
        }
        try {
            //主线程 等待 子线程执行完 等待
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //多线程执行类
    public class ThreadClass implements Runnable{
        long id = 115L;
        private CustomizeCacheService customizeCacheService;

        public ThreadClass(CustomizeCacheService customizeCacheService) {
            this.customizeCacheService = customizeCacheService;
        }

        @Override
        public void run() {
            try {
                //所有子线程在这里等待，当所有线程实例化后，停止等待
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //执行业务方法
            customizeCacheService.getUserInfoById(id);
        }
    }


    @Test
    void testJsonArray() throws ClassNotFoundException {
        JSONArray array = new JSONArray();

        try {
            //生成。class文件
            Class result = Class.forName("com.xiaodai.customize.service.json.JsonToBeanService");
            System.out.println("结果：" + result);
            //转换 string 字符串到 class文件

            JSONObject.parseObject("", result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
