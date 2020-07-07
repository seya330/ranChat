package com.seya330.ranchat.chat.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.seya330.ranchat.chat.service.ChatService;

@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	ChatService chatService;

	@EventListener
	public void handleWebsocketConnectListener(SessionConnectEvent event) {
		StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
		
		String chatType = headerAccessor.getNativeHeader("chatType").get(0);
		HashMap<String, Object> connectMap = new HashMap<String, Object>();
		String sessionId = headerAccessor.getSessionId();
		
		connectMap.put("chatType", chatType);
		connectMap.put("sessionId", sessionId);
		
		if("RANDOM".equals(chatType)) {
			String chatRoomId = headerAccessor.getNativeHeader("chatRoomId").get(0);
	        connectMap.put("chatRoomId", chatRoomId);
			logger.info("[Connected] room id : {} | websocket session id : {}", chatRoomId, sessionId);
		}else if("GROUPCHAT".equals(chatType)) {
			
		}
		
		chatService.connectUser(sessionId, connectMap);
	}

	@EventListener
	public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
		logger.info("socket Disconnected!!");
		StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
		String sessionId = headerAccessor.getSessionId();
		logger.info(headerAccessor.toString());

		logger.info("[Disconnected] websocket session id : {}", sessionId);
		chatService.disconnectUser(sessionId);
	}
}
