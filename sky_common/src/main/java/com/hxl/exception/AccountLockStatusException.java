package com.hxl.exception;

/**
 * 账号被锁定的异常
 */
public class AccountLockStatusException extends BaseException {
    public AccountLockStatusException(String msg){
        super(msg);
    }
}
