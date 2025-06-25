package com.hxl.exception;

/**
 * 订单提交失败异常
 */
public class OrderSubmitFailException extends BaseException {

    public OrderSubmitFailException(String msg){
        super(msg);
    }
}
