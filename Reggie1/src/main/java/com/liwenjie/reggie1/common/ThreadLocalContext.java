package com.liwenjie.reggie1.common;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 16:12
 */
public class ThreadLocalContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
