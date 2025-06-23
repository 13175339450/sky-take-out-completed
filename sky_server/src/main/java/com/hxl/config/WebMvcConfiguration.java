package com.hxl.config;

import com.hxl.interceptor.JwtTokenAdminInterceptor;
import com.hxl.interceptor.JwtTokenUserInterceptor;
import com.hxl.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration //声明这是一个配置类
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义的拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册管理端的拦截器
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**") //拦截的路径
                .excludePathPatterns("/admin/employee/login"); //排除登录

        //注册用户端的拦截器
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")//拦截的路径
                .excludePathPatterns("/user/user/login")//排除用户登录
                .excludePathPatterns("/user/shop/status");//排除获取店铺状态
    }

    /**
     * 通过 knife4j 生成 管理端 接口文档
     * 访问路径： localhost:8080/doc.html
     */
    @Bean
    public Docket docketAdmin() {
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

    /**
     * TODO: 设置静态资源映射
     *      让doc文档生效、INF下的静态页面生效
     *          一旦设置后，系统默认的配置失效
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射");
        //当访问http://localhost:8080/doc.html时，系统会到classpath:/META-INF/resources/目录下查找对应的文件。 classpath是类路径
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        //对于像/webjars/bootstrap/5.3.0/css/bootstrap.min.css这样的请求，系统会到classpath:/META-INF/resources/webjars/目录中查找资源。
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 配置 SpringMVC框架的 消息转换器
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        //为消息转换器设置一个 自定义的对象转换器 对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());

        //将自定义的转换器加入容器里 0表示放在容器所有转化器的前面 不然在后面不会被使用到
        converters.add(0, converter);
    }
}
