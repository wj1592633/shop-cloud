package com.shop.common.enumation;

public enum OrderStateEnum {
    NEED_PAY(0,"待付款中"),
    HAD_PAY(1,"已付款"),
    CANCEL_TIME_OUT(2,"已超时"),
    CANCEL_PAY_USER(3,"已取消")
    ;

    private Integer stateNum;
    private String stateStr;
    private OrderStateEnum(Integer stateNum, String stateStr){
        this.stateNum = stateNum;
        this.stateStr = stateStr;
    }

    public static String getStateStrByStateNum(Integer stateNum){
        OrderStateEnum[] values = OrderStateEnum.values();
        for (OrderStateEnum stateEnum : values){
            if (stateEnum.getStateNum().equals(stateNum)){
                return stateEnum.getStateStr();
            }
        }
        return null;
    }

    public Integer getStateNum() {
        return stateNum;
    }

    public void setStateNum(Integer stateNum) {
        this.stateNum = stateNum;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

}
