package com.shop.common.enumation;


/**
 * 异常消息枚举
 */
public enum ExceptionEnum {
    /** 未知异常 */
    UNKNOWN_EXCEPTION("1000","未知异常"),

    //五权限异常
    NO_PERMISSION("1001","无权操作"),

    //查询的数据不存在
    NOT_EXIST("1002","查无此记录"),

    //数据库操作失败
    DB_OPERATE_ERROR("1004","数据库操作失败"),

    NO_WORK("1007","无效的操作"),

    NULL_DATA("1008","无效的信息"),

    BAD_NET("1009","操作失败，稍后重试!"),

    ERROR_ACCOUNT("1012","账号不存在"),
    ACC_PWD_NO_NULL("1013","账号或密码不能为空"),
    USER_NO_EXSIT("1014","用户不存在"),
    PWD_NOT_RIGHT("1015","密码错误"),
    PWD_OR_ACC_ERROR("1017","账号或密码错误"),
    OPERATE_DATA_NOT_EXSIT("1016","操作的数据不存在"),
    FORBIDEN_AREA("1018","该系统功能已被禁用"),


    USER_INVALID("1020","账号被冻结"),

    LOG_OUT_FAIL("1022","退出失败"),
    NO_RESERVE("1023","商品库存不足"),
    HAD_BOUGHT("1024","已经购买过该物品"),
    GOODS_NO_IN_SALE("1025","该物品已经下架"),
    NO_MONEY("1027","余额不足"),
    NULL_ARGS("1026","参数不能为空"),
    HAD_PAY("1028","已经支付"),
    NEED_LOGIN("401","尚未登录"),
    ILLEGAL_ORDER("1030","无效的订单"),
    TIME_OUT_ORDER("1031","超时的订单")

    ;


    private String errorCode;
    private String errorMsg;

    ExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
