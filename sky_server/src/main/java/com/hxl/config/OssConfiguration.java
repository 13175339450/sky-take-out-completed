package com.hxl.config;

import com.hxl.properties.AliOssProperties;
import com.hxl.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类 用于创建AliOssUtil对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    /**
     * 初始化AliOssUtil对象
     *
     * @param aliOssProperties 使用参数注入 aliOssProperties
     * @return 返回已初始化的AliOssUtil对象
     */
    @Bean
    @ConditionalOnMissingBean //容器里只初始化一个工具类
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("初始化阿里云OSS文件上传工具类对象: {}", aliOssProperties);

        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
