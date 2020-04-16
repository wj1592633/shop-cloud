package com.shop.order.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基础的封装类，应该放入shop-common中
 */
public abstract class BaseWrapper {
    private IPage<Map<String,Object>> page = null;
    private Map<String,Object> single = null;
    private List<Map<String, Object>> mutil = null;

    public BaseWrapper(IPage<Map<String,Object>>  page){
        if(page != null && (page.getRecords() != null)) {
            this.page = page;
            this.mutil = page.getRecords();
        }
    }

    public BaseWrapper(Map<String,Object> single){
        this.single = single;
    }

    public BaseWrapper(List<Map<String, Object>> mutil){
        this.mutil = mutil;
    }


    public Object wrap(){
        if (this.single != null){
            this.wrapTheMap(this.single);
        }else if(this.mutil != null && this.mutil.size() > 0){
            Iterator<Map<String, Object>> iterator = this.mutil.iterator();
            while (iterator.hasNext()){
                this.wrapTheMap(iterator.next());
            }
        }

        if(this.page != null){
            this.page.setRecords(this.mutil);
            return this.page;
        }else {
            if(single != null){
                return this.single;
            }else {
                return this.mutil;
            }
        }

    }

    protected abstract void wrapTheMap(Map<String, Object> entity);
}
