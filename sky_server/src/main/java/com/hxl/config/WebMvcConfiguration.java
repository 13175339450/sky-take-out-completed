package com.hxl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration //声明这是一个配置类
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {


    /**
     * 通过 knife4j 生成 管理端 接口文档
     */
    @Bean
    public Docket docketAdmin(){
        log.info("开始通过knife4j生成接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("sky_take_out接口文档")
                .version("2.0")
                .description("sky_take_out接口文档")
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口") //进行分组
                .apiInfo(apiInfo)
                .select()
                //指定生成接口需要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.hxl.controller.admin"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    /**
     * 通过 knife4j 生成 用户端 接口文档
     */
    @Bean
    public Docket docketUser(){
        log.info("开始通过knife4j生成接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("sky_take_out接口文档")
                .version("2.0")
                .description("sky_take_out接口文档")
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端接口") //进行分组
                .apiInfo(apiInfo)
                .select()
                //指定生成接口需要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.hxl.controller.user"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }
}
