package com.hxl.context;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定义一个类： 里面的成员方法用来 添加id、获取id、删除id
 */
public class BaseContext {

    //创建ThreadLocal对象
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    //将传入的id 设置在ThreadLocal里
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    //从ThreadLocal里获取id
    public static Long getCurrentId(){
        return threadLocal.get();
    }

    //从ThreadLocal里移除id
    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
