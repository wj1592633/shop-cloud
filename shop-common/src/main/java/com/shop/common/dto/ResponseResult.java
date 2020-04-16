package com.shop.common.dto;

import com.shop.common.constant.Constant;

import java.io.Serializable;

public class ResponseResult implements Serializable {
    public ResponseResult(){}
    private int state;
    private Object data;
    private String fail;
    public static ResponseResult ok(){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_SUCCESS;
        return result;
    }
    public static ResponseResult ok(Object data){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_SUCCESS;
        result.data = data;
        return result;
    }
    public static ResponseResult ok(ResponseResult result, Object data){
        result.state = Constant.RESPONSE_CODE_SUCCESS;
        result.data = data;
        return result;
    }

    public static ResponseResult fail(String msg){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_FAIL;
        result.fail = msg;
        return result;
    }
    public static ResponseResult fail(){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_FAIL;
        return result;
    }
    public static ResponseResult unAuthorized(Object data){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_UNAUTHORIZED;
        result.data = data ;
        return result;
    }
    public static ResponseResult unAuthorized(){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_UNAUTHORIZED;
        result.data = "请登录";
        return result;
    }
    public static ResponseResult forbidden(){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_FORBIDDEN;
        result.data = "无权操作";
        return result;
    }
    public static ResponseResult forbidden(Object data){
        ResponseResult result = new ResponseResult();
        result.state = Constant.RESPONSE_CODE_FORBIDDEN;
        result.data = data;
        return result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }
}
