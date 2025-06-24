package com.hxl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启事务管理 MyBatis依赖
@MapperScan("com.hxl.mapper") //自动将mapper包下的接口注册为Spring Bean （不用加@Mapper注解）MyBatis依赖
@EnableCaching //开启SpringCache
@Slf4j //开启日志
public class SkyHXLMain {
    public static void main(String[] args) {
        SpringApplication.run(SkyHXLMain.class, args);
    }
}