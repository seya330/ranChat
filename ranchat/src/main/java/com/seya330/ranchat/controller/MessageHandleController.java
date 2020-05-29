package com.seya330.ranchat.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.seya330.ranchat.bean.ChatRoomRepository;
import com.seya330.ranchat.bean.HelloMessage;
import com.seya330.ranchat.service.ChatService;
import com.seya330.ranchat.util.ServletUtil;
import com.seya330.ranchat.vo.ChatMessage;
import com.seya330.ranchat.vo.ChatRequest;
import com.seya330.ranchat.vo.ChatResponse;
import com.seya330.ranchat.vo.MessageType;

@RestController
public class MessageHandleController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageHandleController.class); 
	
	@Autowired
	ChatRoomRepository roomRepo;
	
	@Autowired
	SimpMessagingTemplate simpleTemplate;
	
	@Autowired
	SimpMessageSendingOperations simpleOperation; 
	
	@Autowired
	ChatService chatService;
	
	@GetMapping("/join")
	public DeferredResult<ChatResponse> joinRequest(HelloMessage message){
		String sessionId = ServletUtil.getSession().getId();
		logger.info("join request. session id : {}, join name : {}", sessionId, message.getName());
		final ChatRequest user = new ChatRequest(sessionId);
		final DeferredResult<ChatResponse> deferredResult = new DeferredResult<>(null);
		chatService.joinChatRoom(user, deferredResult);
		return deferredResult;
	}
	
	@MessageMapping("/chat.message/{chatRoomId}")
	public void sendMessage(@DestinationVariable("chatRoomId") String chatRoomId, @Payload ChatMessage chatMessage) {
		logger.info("Request message. roomd id : {} | chat message : {} | principal : {}", chatRoomId, chatMessage);
		if (!StringUtils.hasText(chatRoomId) || chatMessage == null) {
            return;
        }
		
		if (chatMessage.getMessageType() == MessageType.CHAT) {
            chatService.sendMessage(chatRoomId, chatMessage);
        }
	}
	
}
