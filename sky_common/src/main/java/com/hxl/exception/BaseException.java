package com.hxl.exception;

/**
 * 受检查异常（Checked Exception）：
 *      这类异常继承自Exception类（但不包含RuntimeException）。
 *          在代码里使用时，Java 编译器强制要求你对其进行处理，处理方式有两种：
 *              要么用try-catch捕获，要么在方法签名中用throws声明。
 * 未检查异常（Unchecked Exception）：
 *      继承自RuntimeException的异常属于此类。
 *          使用时，编译器不会强制你处理，你可以自行选择是否处理。
 */
public class BaseException extends RuntimeException {

    public BaseException(){

    }

    public BaseException(String msg){
        super(msg);
    }
}
