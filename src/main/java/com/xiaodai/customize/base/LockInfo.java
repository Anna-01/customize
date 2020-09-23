package com.xiaodai.customize.base;

import java.util.concurrent.locks.Lock;

/**
 * @author ljx
 */
public class LockInfo {
    /**
     *
     */
    public Lock lock;
    /**
     * 锁标识位
     */
    public boolean lockFlag;

    public LockInfo() {
        lock = null;
        lockFlag = false;
    }

    public LockInfo(Lock lock, boolean lockFlag) {
        this.lock = lock;
        this.lockFlag = lockFlag;
    }
}