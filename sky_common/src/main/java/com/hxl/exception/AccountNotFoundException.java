package com.hxl.exception;

/**
 * 账户不存在的自定义异常
 */
public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(String msg){
        super(msg);
    }
}
