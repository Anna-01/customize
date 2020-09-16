package com.xiaodai.customize.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 实现线程安全的map
 * @author My
 */
public class SafeMap {
    public static Logger logger = LoggerFactory.getLogger(SafeMap.class);

    private static HashMap<String, Integer> safeMap = new HashMap();

    private static volatile Integer opator = 1;

    /**
     *存入key
     */
    public static void setKey(String mName)  {
        synchronized (safeMap) {
            if (safeMap.size() == 0) {
                try {
                    logger.info(Thread.currentThread().getName() + mName + "setKeyWait");
                    safeMap.put(mName, opator);
                    safeMap.wait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    logger.info(Thread.currentThread().getName() + mName +"setKeyNotify");
                    safeMap.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            if (safeMap.size() != 0) {
                if (safeMap.containsKey(mName)) {
                    try {
                        safeMap.remove(mName);
                        logger.info(Thread.currentThread().getName() + mName + "getKeyWait");
                        safeMap.wait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    logger.info(Thread.currentThread().getName() + mName + "getKeyNotify");
                    safeMap.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
