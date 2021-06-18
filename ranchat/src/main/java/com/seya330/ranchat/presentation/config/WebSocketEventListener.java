package com.seya330.ranchat.presentation.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.seya330.ranchat.application.chat.service.ChatService;
import com.seya330.ranchat.share.util.JwtUtil;
import com.seya330.ranchat.core.user.vo.RegUserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	ChatService chatService;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valueOperation;
	
	@Resource(name="redisTemplate")
	private SetOperations<String, Object> setOperation;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, Object> hashOperation;
	
	@Resource(name="redisUserTemplate")
	private ValueOperations<String, RegUserVO> redisLoginOperation;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@EventListener
	public void handleWebsocketConnectListener(SessionConnectEvent event) {
		StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
		
		String chatType = headerAccessor.getNativeHeader("chatType").get(0);
		Map<String, Object> connectMap = new HashMap<String, Object>();
		String sessionId = headerAccessor.getSessionId();
		connectMap.put("chatType", chatType);
		connectMap.put("sessionId", sessionId);
		headerAccessor.getSessionAttributes().put("chatType", chatType);
		if("RANDOM".equals(chatType)) {
			String chatRoomId = headerAccessor.getNativeHeader("chatRoomId").get(0);
	        connectMap.put("chatRoomId", chatRoomId);
	        chatService.connectUser(sessionId, connectMap);
			logger.info("[Connected] room id : {} | websocket session id : {}", chatRoomId, sessionId);
		}else if("GROUPCHAT".equals(chatType)) {
			
			String token = headerAccessor.getNativeHeader("auth-token").get(0);
			log.info("ws connect - token : " + token);
			RegUserVO user = new RegUserVO();
			user.setUniqId(jwtUtil.getJwtDataFromKey(token, "uniqId"));
			redisLoginOperation.set(sessionId, user);
		}else if("TOPIC".equals(chatType)){
			String token = headerAccessor.getNativeHeader("auth-token").get(0);
			log.info("ws connect - token : " + token);
			RegUserVO user = new RegUserVO();
			user.setUniqId(jwtUtil.getJwtDataFromKey(token, "uniqId"));
		}
	}

	@EventListener
	public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
		logger.info("socket Disconnected!!");
		StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
		String sessionId = headerAccessor.getSessionId();
		String chatType = (String)headerAccessor.getSessionAttributes().get("chatType");
		if("GROUPCHAT".equals(chatType)) {
			redisLoginOperation.getOperations().delete(sessionId);
		}else if("TOPIC".equals(chatType)){
			String token = headerAccessor.getNativeHeader("auth-token").get(0);
			log.info("ws connect - token : " + token);
			RegUserVO user = new RegUserVO();
			user.setUniqId(jwtUtil.getJwtDataFromKey(token, "uniqId"));
		}else {
			logger.info("[Disconnected] websocket session id : {}", sessionId);
			chatService.disconnectUser(sessionId);
		}
	}
}
