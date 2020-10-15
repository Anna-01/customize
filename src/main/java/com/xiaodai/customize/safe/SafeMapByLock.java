package com.xiaodai.customize.safe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock实现线程安全
 *
 * @author My
 */
public class SafeMapByLock {
    public static Logger logger = LoggerFactory.getLogger(SafeMapByLock.class);

    private static HashMap<String, Integer> safeMap = new HashMap();

    private static volatile Integer opator = 1;

    //创建锁对象
    //reentrant 可重入锁
    private static Lock lock = new ReentrantLock();
    //创建锁的条件，情况
    private static Condition condition = lock.newCondition();


    /**
     * 存入key
     * 1。假设有两个线程 a，b   ； a的setkey获取锁safeMap 放入值 然后 阻塞 释放锁 ，
     * 2。线程b 获取到锁 ， 判断safeMap此时已经不等于0了 所以唤醒 wait
     * 3。线程a从wait开始处继续执行
     */
    public static void setKey(String mName) {
        lock.lock();
        try {
            if (safeMap.size() == 0) {
                try {
                    logger.info(Thread.currentThread().getName() + mName + "setKeyWait");
                    safeMap.put(mName, opator);
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    logger.info(Thread.currentThread().getName() + mName + "setKeyNotify");
                    condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    /**
     * 取出key并比较
     *
     * @param mName
     * @param
     */
    public static void getKey(String mName) {
        logger.info("map的size={}", safeMap.size());
        lock.lock();
        try {
            if (safeMap.size() != 0) {
                if (safeMap.containsKey(mName)) {
                    try {
                        safeMap.remove(mName);
                        logger.info(Thread.currentThread().getName() + mName + "getKeyWait");
                        condition.await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

                try {
                    logger.info(Thread.currentThread().getName() + mName + "getKeyNotify");
                   condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
