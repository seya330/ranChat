package com.seya330.ranchat.listener;

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

import com.seya330.ranchat.service.ChatService;

@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	ChatService chatService;
	
	@EventListener
	public void handleWebsocketConnectListener(SessionConnectEvent event) {
		MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
		//GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader("simpConnectMessage");
		Map<String, Object> nativeHeaders = (Map<String, Object>) accessor.getHeader("nativeHeaders");
		String chatRoomId = ((List<String>) nativeHeaders.get("chatRoomId")).get(0);
		String sessionId = (String) accessor.getHeader("simpSessionId");
		
		/*StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("## headerAccessor :: " + headerAccessor);
        String chatRoomId = headerAccessor.getNativeHeader("chatRoomId").get(0);
        String sessionId = headerAccessor.getSessionId();*/
		
		logger.info("[Connected] room id : {} | websocket session id : {}", chatRoomId, sessionId);
		
		chatService.connectUser(chatRoomId, sessionId);
	}
	
	@EventListener
	public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
		logger.info("socket Disconnected!!");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		
		logger.info("[Disconnected] websocket session id : {}", sessionId);
		chatService.disconnectUser(sessionId);
	}
}
