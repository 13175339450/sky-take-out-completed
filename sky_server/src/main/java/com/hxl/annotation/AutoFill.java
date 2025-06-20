package com.hxl.annotation;

import com.hxl.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: 自定义注解类
 *       配合AOP编程 对某些公共字段进行自动填充 (insert、update操作)
 */
@Target(ElementType.METHOD) //作用域： 作用在方法上
@Retention(RetentionPolicy.RUNTIME) //生命周期： 运行时
public @interface AutoFill {

    /* TODO： value() 是成员变量
     *      参数类型为： OperationType 枚举类
     *            枚举类里有 INSERT、UPDATE 两种操作类型
     */
    OperationType value();
}
