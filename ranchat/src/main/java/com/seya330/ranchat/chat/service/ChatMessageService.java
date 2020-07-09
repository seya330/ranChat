package com.seya330.ranchat.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.seya330.ranchat.chat.dao.ChatDAO;
import com.seya330.ranchat.chat.dao.ChatMessageDAO;
import com.seya330.ranchat.chat.dao.ChatRoomDAO;
import com.seya330.ranchat.chat.vo.ChatMessageVO;
import com.seya330.ranchat.chat.vo.ChatRoomJoinnerVO;
import com.seya330.ranchat.util.IDGeneratorUtil;

@Service
public class ChatMessageService {
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);
	
	@Autowired
	private ChatDAO chatDAO;
	
	@Autowired
	private ChatMessageDAO chatMessageDAO;
	
	@Autowired
	private ChatRoomDAO chatRoomDAO;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	public ArrayList<ChatMessageVO> getChatMessageList(ChatMessageVO chatMessageVO){
		chatMessageDAO.updateChatMessageInfo(chatMessageVO);
		return chatDAO.selectChatMessage(chatMessageVO);
	}
	
	public void sendChatMessage(ChatMessageVO chatMessageVO) {
		this.insertChatMessageLogic(chatMessageVO);
		
		ChatRoomJoinnerVO joinnerVO = new ChatRoomJoinnerVO();
		joinnerVO.setChatRoomId(chatMessageVO.getChatRoomId());
		List<ChatRoomJoinnerVO> joinners = chatRoomDAO.selectChatRoomJoinner(joinnerVO);
		chatMessageVO.setRegDate(chatMessageVO.getSendDateString());
		for(ChatRoomJoinnerVO joinner : joinners) {
			String pubsubscribe = "sub.public." + joinner.getUniqId() + ".message";
			logger.info(pubsubscribe);
			messagingTemplate.convertAndSend("/sub/public." + joinner.getUniqId() + ".message", chatMessageVO);
		}
	}
	
	public void insertChatMessageLogic(ChatMessageVO chatMessageVO) {
		chatMessageVO.setMessageId(IDGeneratorUtil.generateId("M", 19));
		chatMessageDAO.insertChatRoomMessage(chatMessageVO);
		chatMessageDAO.insertChatMessageInfoQuery(chatMessageVO);
	}
	
	public void readChatMessage(ChatMessageVO chatMessageVO) {
		chatMessageDAO.updateChatMessageInfo(chatMessageVO);
	}
}
