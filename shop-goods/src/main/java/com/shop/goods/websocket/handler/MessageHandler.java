package com.shop.goods.websocket.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//@Component
public class MessageHandler extends TextWebSocketHandler {
    private static Map<String,WebSocketSession> userContainer = new ConcurrentHashMap<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //System.out.println("收到消息-------"+message.getPayload());
        String msg = message.getPayload();

        JsonNode jsonNode = MAPPER.readTree(msg);
        /*JsonNode jsonNode = MAPPER.readTree(msg);
        jsonNode.get("toId");
        String msg1 = jsonNode.get("msg").asText();
        Message data = new Message();
        data.setMsg(msg1);
        mongodbDao.save(data);*/
        WebSocketSession to = userContainer.get(jsonNode.get("to").asText());
        if(to != null && to.isOpen()) {
            //session.sendMessage(new TextMessage("好的，收到了"));
            to.sendMessage(new TextMessage(msg));
            //mongodbDao.updateMessgageState(1);//更新为已读
        }
        //super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("已经连接上系统-------");
        session.sendMessage(new TextMessage("好的，已经连接上系统"));
        String uid = (String)session.getAttributes().get("uid");
        userContainer.put(uid,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        session.close();
        userContainer.remove((String)session.getAttributes().get("uid"));
        System.out.println("断开连接========"+((String)session.getAttributes().get("uid")));
    }
}
