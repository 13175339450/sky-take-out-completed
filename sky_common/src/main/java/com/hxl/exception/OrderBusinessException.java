package com.hxl.exception;

/**
 * 自定义异常类: 订单业务异常
 */
public class OrderBusinessException extends BaseException {

    public OrderBusinessException(String msg){
        super(msg);
    }
}
