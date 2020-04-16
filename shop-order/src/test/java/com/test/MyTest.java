package com.test;

public class MyTest {
    public static void main(String[] args) {

    }
}
enum StateCodeEnum{
    SUCESS(200,"成功"),
    FAIL(404,"失败")
    ;

    int code;
    String msg;

   private StateCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}