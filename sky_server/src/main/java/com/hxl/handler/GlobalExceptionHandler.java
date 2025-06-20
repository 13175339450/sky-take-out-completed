package com.hxl.handler;

import com.hxl.constant.MessageConstant;
import com.hxl.exception.BaseException;
import com.hxl.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器： 处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获业务异常
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException e){
        log.info("异常信息: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * SQL完整性约束异常处理
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e){
        String msg = e.getMessage();

        //判断异常类型 唯一性约束 (新增员工的username已存在会抛出的异常)
        if (msg.contains("Duplicate entry")){
            //用户名已存在
            return Result.error(MessageConstant.USERNAME_ALREADY_EXIST);
        } else {
            //未知错误
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
