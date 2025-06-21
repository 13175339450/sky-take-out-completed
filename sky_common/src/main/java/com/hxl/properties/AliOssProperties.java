package com.hxl.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO: 配置属性类:
 *          读取application里的 alioss的配置项
 *              并且封装成一个java对象
 *                  可以直接@AutoWired注入该配置属性类
 */
@Component //加入ioc容器
@ConfigurationProperties(prefix = "hxl.alioss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliOssProperties {
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
