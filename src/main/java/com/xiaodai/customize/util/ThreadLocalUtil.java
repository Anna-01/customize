package com.xiaodai.customize.util;

/**
 * threadLocal工具类
 * @author 战66
 */
public class ThreadLocalUtil {

    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static void setThreadLocalValue(Object object) {
        threadLocal.set(object);
    }

    public static Object getThreadLocalValue() {

        Object result = threadLocal.get();
        return result;
    }
}
