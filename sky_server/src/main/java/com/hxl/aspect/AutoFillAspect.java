package com.hxl.aspect;

import com.hxl.annotation.AutoFill;
import com.hxl.constant.AutoFillConstant;
import com.hxl.context.BaseContext;
import com.hxl.enumeration.OperationType;
import com.hxl.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * AOP 切片类
 */
@Component //加入ioc容器
@Aspect //切面注解
@Slf4j
public class AutoFillAspect {
    /**
     * 自定义切点
     *     指定拦截对应包下的方法 并且筛选出 加了@AutoFill注解的方法
     *          只扫描mapper包下的方法上的注解
     */
    @Pointcut("execution(* com.hxl.mapper.*.*(..)) && @annotation(com.hxl.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知：
     *      在切点拦截到的方法(INSERT/UPDATE类型的方法)执行前
     *          执行如下方法
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("对公共字段进行自动填充");

        //0.准备要自动填充的数据 当前时间 和 当前员工id
        LocalDateTime now = LocalDateTime.now();
        Long empId = BaseContext.getCurrentId();

        //1.获取 连接点的方法签名（signature） 并且向下转型(可以使用子类的方法)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //2.获取mapper包下加了@AutoFill的方法 的注解的实体对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);

        //3.获取该实体对象的属性值 value属性 判断是INSERT还是UPDATE
        OperationType value = autoFill.value(); //类型是 自定的 操作类型枚举类

        //4.获取当前mapper包下方法的参数里的 实体对象 （约定放在参数第一位置）
        Object[] entityList = joinPoint.getArgs();

        //5.空指针和空参判断
        if (entityList == null || entityList.length == 0) return;

        //5.获取实体
        Object entity = entityList[0];//当前实体类任意 Employee、Dish...

        //6.利用反射 获取实体对象里的set方法(方法名 + 参数类型) 并且调用invoke对共有字段赋值
        if (value == OperationType.INSERT){
            try {
                //反射获取 entity实体类里的 所有set方法 (包括私有的) TODO：entity实体类必须有set方法
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //调用函数进行赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, empId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (value == OperationType.UPDATE){
            try {
                //反射获取 entity实体类里的 所有set方法 (包括私有的) TODO：entity实体类必须有set方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //调用函数进行赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
