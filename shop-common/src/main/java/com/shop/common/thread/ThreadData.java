package com.shop.common.thread;

/**
 * 用于存储线程自己独有的变量
 * @param <T>
 */
public class ThreadData<T> {
    private T data;
    private static ThreadLocal<ThreadData> threadLocal = new ThreadLocal<>();
    private ThreadData(){}

    public static ThreadData instance(){
        ThreadData instance = threadLocal.get();
        if (null == instance){
            instance = new ThreadData();
            threadLocal.set(instance);
        }
        return instance;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
