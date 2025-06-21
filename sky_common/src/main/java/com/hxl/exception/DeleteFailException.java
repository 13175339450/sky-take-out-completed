package com.hxl.exception;

/**
 * 自定义删除方法异常
 */
public class DeleteFailException extends BaseException {
    public DeleteFailException(String msg){
        super(msg);
    }
}
