package com.shop.common.exception;

import com.shop.common.enumation.ExceptionEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private String errorCode; //异常code
    private String errorMsg; //异常信息

    public CustomException(String errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public CustomException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getErrorMsg());
        this.errorCode = exceptionEnum.getErrorCode();
        this.errorMsg = exceptionEnum.getErrorMsg();
    }
}
