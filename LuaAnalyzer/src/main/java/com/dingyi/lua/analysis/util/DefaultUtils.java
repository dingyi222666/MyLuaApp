package com.dingyi.lua.analysis.util;

/**
 * @author: dingyi
 * @date: 2021/9/6 22:04
 * @description:
 **/
public class DefaultUtils {


    public static long measureTime(Runnable runnable) {
        long startTime =System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis()-startTime;
    }

}
