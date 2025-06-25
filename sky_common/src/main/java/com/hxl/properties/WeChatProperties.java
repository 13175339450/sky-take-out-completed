package com.hxl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付相关配置属性类
 * 从配置文件中读取以 "hxl.wechat" 为前缀的配置项
 */
@Component
@ConfigurationProperties(prefix = "hxl.wechat")
@Data
public class WeChatProperties {

    /** 微信小程序的AppID，用于标识小程序身份 */
    private String appid;

    /** 微信小程序的Secret，与AppID配合使用的密钥 */
    private String secret;

    /** 微信支付商户号，用于商户身份识别 */
    private String mchid;

    /** 商户API证书的证书序列号，用于API调用时的身份验证 */
    private String mchSerialNo;

    /** 商户私钥文件路径，用于签名API请求 */
    private String privateKeyFilePath;

    /** API V3密钥，用于解密微信支付返回的敏感信息 */
    private String apiV3Key;

    /** 微信支付平台证书路径，用于验证微信支付响应的签名 */
    private String weChatPayCertFilePath;

    /** 支付成功的回调通知URL，微信支付结果将通知到该地址 */
    private String notifyUrl;

    /** 退款成功的回调通知URL，微信退款结果将通知到该地址 */
    private String refundNotifyUrl;
}