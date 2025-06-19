package com.hxl.exception;


/**
 * 自定义异常类： 继承Exception
 * 登录失败异常
 */
public class LoginFailException extends BaseException{

    /**
     * 登录失败的异常
     * @param msg
     */
    public LoginFailException(String msg){
        super(msg);
    }
}
