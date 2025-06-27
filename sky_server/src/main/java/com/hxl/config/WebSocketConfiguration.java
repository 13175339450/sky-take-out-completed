package com.hxl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类：负责WebSocket组件的注册与初始化
 * 该类通过Spring的JavaConfig方式替代传统XML配置，启用WebSocket功能支持
 */
@Configuration
public class WebSocketConfiguration {

    /**
     * 注册ServerEndpointExporter Bean
     * 该Bean是Spring WebSocket的核心组件，负责扫描并注册所有带有@ServerEndpoint注解的类
     * 被注册的类将作为WebSocket端点，处理客户端连接与消息通信
     *
     * @return ServerEndpointExporter实例
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}