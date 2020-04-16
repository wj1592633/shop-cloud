package com.shop.test;

import java.io.Serializable;

/**
 * 线程范围内共享数据
 */
public class LogThreadData implements Serializable {
    private LogThreadData(){}
    //data 为一条线程内的共享数据
    private Object data;
    private static ThreadLocal<LogThreadData> threadLocal = new ThreadLocal<>();
    public static LogThreadData instance(){
        LogThreadData instance  = threadLocal.get();
        if(instance == null){
            instance =  new LogThreadData();
            threadLocal.set(instance);
        }
        return instance;
    }

    /**
     * 释放资源
     */
    public static void release(){
        threadLocal.remove();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}