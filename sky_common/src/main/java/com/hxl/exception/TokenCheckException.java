package com.hxl.exception;

/**
 * 自定义异常类 用于检测token的有效期等信息
 */
public class TokenCheckException extends BaseException {

    public TokenCheckException(String msg){
        super(msg);
    }
}
