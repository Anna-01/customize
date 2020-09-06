package com.xiaodai.customize.aop;

import java.util.HashMap;

/**
 * @author My
 */
public class SafeMap {
    private static HashMap<String, Integer> safeMap = new HashMap();

    private static volatile Integer opator = 1;

    /**
     *
     */
    public static void setKey(String mName)  {
        synchronized (safeMap) {
            if (safeMap.size() == 0) {
                safeMap.put(mName, opator);
                System.out.println(Thread.currentThread().getName() + "notify");
                safeMap.notify();
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + "wait");
                    safeMap.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param mName
     * @param
     */
    public static void getKey(String mName)  {
        synchronized (safeMap) {
            if (safeMap.size() != 0) {
                if (safeMap.containsKey(mName)) {
                    safeMap.remove(mName);
                    System.out.println(Thread.currentThread().getName() + "notify");
                    safeMap.notify();
                }
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + "wait");
                    safeMap.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
