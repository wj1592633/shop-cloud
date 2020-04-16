package com.shop.user.exception;

import com.shop.common.constant.Constant;
import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class HandleGlobalException {

   /* @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public ResponseResult handleUnauthorizedException (Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return ResponseResult.fail("无权访问");
    }*/


    @ExceptionHandler({java.lang.NullPointerException.class})
    @ResponseBody
    public ResponseResult handleNullPointerException(Exception e) {
        log.error(e.getMessage());
        log.error(e.toString());
        return ResponseResult.fail("请正确填写信息!");
    }

    @ExceptionHandler({CustomException.class})
    public ResponseResult handleCustomException(CustomException e) {
        log.error(e.getMessage());
        log.error(e.toString());
        if (Integer.valueOf(e.getErrorCode()).intValue() == Constant.RESPONSE_CODE_UNAUTHORIZED){
            return ResponseResult.unAuthorized(e.getErrorMsg());
        }
        return ResponseResult.fail(e.getErrorMsg());
    }

   /* @ExceptionHandler({DisabledAccountException.class})
    @ResponseBody
    public ResponseResult handleDisabledAccountException (Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return ResponseResult.fail("您的账号被禁用了");
    }*/

    /* @ExceptionHandler({LockedAccountException.class})
     @ResponseBody
     public ResponseResult handleLockedAccountException (Exception e){
         System.out.println(e.getMessage());
         e.printStackTrace();
         return ResponseResult.fail("您的账号被冻结了，请联系管理员");
     }

     @ExceptionHandler({AuthenticationException.class})
     @ResponseBody
     public ResponseResult handleAuthenticationException (Exception e){
         System.out.println(e.getMessage());
         e.printStackTrace();
         return ResponseResult.fail("登陆出错");
     }
 */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseResult handleAccessDeniedException(Exception e) {
        log.error(e.getMessage());
        log.error(e.toString());
        return ResponseResult.forbidden();
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult handleException(Exception e) {
        log.error(e.getMessage());
        log.error(e.toString());
        return ResponseResult.fail("无效的操作 ( ╯□╰ )");
    }
}