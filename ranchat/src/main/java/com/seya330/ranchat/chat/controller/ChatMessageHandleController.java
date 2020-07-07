package com.seya330.ranchat.chat.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.seya330.ranchat.chat.bean.ChatRoomRepository;
import com.seya330.ranchat.chat.bean.ChatUserBean;
import com.seya330.ranchat.chat.service.ChatMessageService;
import com.seya330.ranchat.chat.service.ChatService;
import com.seya330.ranchat.chat.vo.ChatMessageVO;
import com.seya330.ranchat.chat.vo.ChatResponse;
import com.seya330.ranchat.chat.vo.MessageType;
import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.util.ServletUtil;

@RestController
public class ChatMessageHandleController {

	private static final Logger logger = LoggerFactory.getLogger(ChatMessageHandleController.class); 
	
	@Autowired
	ChatRoomRepository roomRepo;
	
	@Autowired
	SimpMessagingTemplate simpleTemplate;
	
	@Autowired
	SimpMessageSendingOperations simpleOperation; 
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	ChatMessageService chatMessageService;
	/**
	 * 메세지 발송 
	 * */
	@MessageMapping("/chat.message/{chatRoomId}")
	public void sendMessage(@DestinationVariable("chatRoomId") String chatRoomId, @Payload ChatMessageVO chatMessage) {
		logger.info("Request message. roomd id : {} | chat message : {} | principal : {}", chatRoomId, chatMessage);
		if (!StringUtils.hasText(chatRoomId) || chatMessage == null) {
            return;
        }
		
		if (chatMessage.getMessageType() == MessageType.CHAT) {
            chatService.sendMessage(chatRoomId, chatMessage);
        }
	}
	
	@MessageMapping("/groupChat.message/send")
	public void sendGroupChatMessage(@Payload ChatMessageVO chatMessageVO, StompHeaderAccessor headerAccessor) {
		String authToken = "";
		authToken += headerAccessor.getNativeHeader("auth-token").get(0);
		chatMessageVO.setSenderId(jwtUtil.getJwtDataFromKey(authToken, "uniqId"));
		chatMessageVO.setSendUserId(jwtUtil.getJwtDataFromKey(authToken, "userId"));
		chatMessageService.sendChatMessage(chatMessageVO);
	}
}
