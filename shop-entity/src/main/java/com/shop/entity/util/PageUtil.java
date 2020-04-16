package com.shop.entity.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页对象
 * @param <T>
 */
public class PageUtil<T> extends Page<T>{
    private long startRecord;//开始的记录
    private Object keyword; //用于搜索的关键字
    private Object keyword2;////备用搜索的关键字
    private Object keyword3;
    private Object keyword4;
    private Object keyword5;

    @Override
    public Page<T> setCurrent(long current) {
        if(current < 1){
            current = 1;
        }
        return super.setCurrent(current);
    }

    public long getStartRecord() {
        if(startRecord < 0){
            startRecord = 0;
        }
        this.startRecord = (this.getCurrent() - 1) * this.getSize();
        return this.startRecord;
    }

    public void setStartRecord(long startRecord) {
        if(startRecord < 0){
            startRecord = 0;
        }
        this.startRecord = startRecord;
    }

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public Object getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(Object keyword2) {
        this.keyword2 = keyword2;
    }

    public Object getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(Object keyword3) {
        this.keyword3 = keyword3;
    }

    public Object getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(Object keyword4) {
        this.keyword4 = keyword4;
    }

    public Object getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(Object keyword5) {
        this.keyword5 = keyword5;
    }
}
