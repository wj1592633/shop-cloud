package com.shop.goods.websocket.config;

import com.shop.goods.websocket.handler.MessageHandler;
import com.shop.goods.websocket.intercepter.MessageIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 总结:WebSocketConfig为总配置类，再本类中的方法registerWebSocketHandlers添加MessageHandler和MessageIntercepter
 * MessageHandler --为具体处理消息，封装、存储等
 * MessageIntercepter --为拦截器
 */
/*@Configuration
@EnableWebSocket*/
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageIntercepter messageIntercepter;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messageHandler,"/ws/{uid}")//{uid}占位符
                .addInterceptors(messageIntercepter)
        .setAllowedOrigins("*");//要可以跨域，不然前端访问不了
    }
}
