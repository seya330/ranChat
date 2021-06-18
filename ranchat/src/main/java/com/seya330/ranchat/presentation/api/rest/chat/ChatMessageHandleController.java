package com.seya330.ranchat.presentation.api.rest.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.seya330.ranchat.core.chat.domain.ChatRoomRepository;
import com.seya330.ranchat.application.chat.service.ChatMessageService;
import com.seya330.ranchat.application.chat.service.ChatService;
import com.seya330.ranchat.application.chat.service.TopicChatService;
import com.seya330.ranchat.core.chat.vo.ChatMessageVO;
import com.seya330.ranchat.core.chat.vo.MessageType;
import com.seya330.ranchat.core.chat.vo.TopicChatMessageVO;
import com.seya330.ranchat.share.util.JwtUtil;
import com.seya330.ranchat.core.user.vo.RegUserVO;

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
	
	@Autowired
	TopicChatService topicChatService;
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
		RegUserVO user = jwtUtil.getUserByToken(authToken);
		chatMessageVO.setSenderId(user.getUniqId());
		chatMessageVO.setSendUserId(user.getUserId());
		chatMessageService.sendChatMessage(chatMessageVO);
	}
	
	@MessageMapping("/topic.message/send")
	public void sendTopicChatMessage(@Payload TopicChatMessageVO message, StompHeaderAccessor headerAccessor) {
		String authToken = "";
		authToken += headerAccessor.getNativeHeader("auth-token").get(0);
		RegUserVO user = jwtUtil.getUserByToken(authToken);
		message.setSenderId(user.getUniqId());
		message.setSendUserId(user.getUserId());
		topicChatService.sendTopicMessage(message);
	}
}
