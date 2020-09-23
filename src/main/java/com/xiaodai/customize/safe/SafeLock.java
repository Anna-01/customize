package com.xiaodai.customize.safe;

import com.xiaodai.customize.base.LockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author My
 */
public class SafeLock {

    private static Logger logger = LoggerFactory.getLogger(SafeLock.class);

    private static Lock lock = new ReentrantLock();

    /**
     * 获取锁
     */
    public static  LockInfo reenterLock(long time, TimeUnit timeUnit) {

        LockInfo lockInfo = new LockInfo();
        lockInfo.lock = lock;
        try {
            lockInfo.lockFlag = lock.tryLock(time, timeUnit);
        } catch (Exception e) {
            // lockFlag=true为未锁定，异常请求允许其继续执行
            lockInfo.lockFlag = true;
            logger.error("getLockInfo error ！", e);
        }
        return lockInfo;
    }

    /**
     * 释放锁
     */
    public static void unLock() {

        lock.unlock();
    }
}
