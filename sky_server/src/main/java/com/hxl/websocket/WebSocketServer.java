package com.hxl.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket服务端点
 * 处理客户端连接、消息收发和断开逻辑
 * 支持通过URL路径参数{sid}区分不同客户端
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // 存储所有在线客户端的会话对象
    private static final Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 客户端连接建立时触发
     * @param session 客户端会话对象
     * @param sid 客户端唯一标识
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "建立连接");
        sessionMap.put(sid, session);
    }

    /**
     * 收到客户端消息时触发
     * @param message 客户端发送的文本消息
     * @param sid 客户端唯一标识
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
    }

    /**
     * 客户端连接断开时触发
     * @param sid 客户端唯一标识
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        sessionMap.remove(sid);
    }

    /**
     * 向所有在线客户端群发消息
     * @param message 需要发送的文本消息
     */
    public void sendToAllClient(String message) {
        for (Session session : sessionMap.values()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}