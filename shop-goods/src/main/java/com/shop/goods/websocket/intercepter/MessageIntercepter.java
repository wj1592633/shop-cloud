package com.shop.goods.websocket.intercepter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
//@Component
public class MessageIntercepter implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) throws Exception {
        String path = serverHttpRequest.getURI().getPath();
        String pathUid = path.substring(path.lastIndexOf("/"));
        pathUid = pathUid.replace("/","");
        //map是WebSocketSession的一个变量。通过session.getAttributes().get("uid");获取
        map.put("uid",pathUid);

        System.out.println("握手，连接上websocket");
        return true;//true接受连接
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
