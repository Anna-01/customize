package com.xiaodai.customize.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 *
 * Lock实现线程安全
 * @author My
 */
public class SafeMapByLock {
    public static Logger logger = LoggerFactory.getLogger(SafeMapByLock.class);

    private static HashMap<String, Integer> safeMap = new HashMap();

    private static volatile Integer opator = 1;

    /**
     *存入key
     * 1。假设有两个线程 a，b   ； a的setkey获取锁safeMap 放入值 然后 阻塞 释放锁 ，
     * 2。线程b 获取到锁 ， 判断safeMap此时已经不等于0了 所以唤醒 wait
     * 3。线程a从wait开始处继续执行
     */
    public static void setKey(String mName)  {
        synchronized (safeMap) {
            while (safeMap.size() == 0) {
                try {
                    logger.info(Thread.currentThread().getName() + mName + "setKeyWait");
                    safeMap.put(mName, opator);
                    safeMap.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                logger.info(Thread.currentThread().getName() + mName +"setKeyNotify");
                safeMap.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *取出key并比较
     * @param mName
     * @param
     */
    public static void getKey(String mName)  {
        synchronized (safeMap) {
            logger.info("map的size={}", safeMap.size());
            while (safeMap.size() != 0) {
                if (safeMap.containsKey(mName)) {
                    try {
                        safeMap.remove(mName);
                        logger.info(Thread.currentThread().getName() + mName + "getKeyWait");
                        safeMap.wait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
                try {
                    logger.info(Thread.currentThread().getName() + mName + "getKeyNotify");
                    safeMap.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }
}
