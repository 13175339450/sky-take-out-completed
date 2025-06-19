package com.hxl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT令牌配置属性类
 */

//自动注入到Spring容器中管理
@Component
//从application.yml 或 application.properties中读取hxl.jwt前缀的配置项
@ConfigurationProperties(prefix = "hxl.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工JWT配置
     * adminSecretKey: JWT签名密钥（用于生成和验证Token）
     * adminTtl: Token有效期（毫秒）
     * adminTokenName: Token在HTTP请求头中的字段名
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户JWT配置
     * userSecretKey: 微信用户JWT签名密钥
     * userTtl: 微信用户Token有效期
     * userTokenName: 微信用户Token在请求头中的字段名
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}