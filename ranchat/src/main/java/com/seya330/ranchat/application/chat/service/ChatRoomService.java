package com.seya330.ranchat.application.chat.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seya330.ranchat.infrastructure.chat.ChatDAO;
import com.seya330.ranchat.infrastructure.chat.ChatRoomDAO;
import com.seya330.ranchat.core.chatroom.vo.ChatRoomJoinnerVO;
import com.seya330.ranchat.core.chatroom.vo.ChatRoomVO;

@Service
public class ChatRoomService {
	private static final Logger logger = LoggerFactory.getLogger(ChatRoomService.class);
	@Autowired
	private ChatRoomDAO chatRoomDAO;
	
	@Autowired
	private ChatDAO chatDAO;
	
	public ArrayList getChatRoomList(ChatRoomVO chatRoomVO) {
		ArrayList<ChatRoomVO> chatRoomList = chatRoomDAO.selectChatRoom(chatRoomVO);
		for(ChatRoomVO chatRoom : chatRoomList) {
			ChatRoomJoinnerVO joinnerVO = new ChatRoomJoinnerVO();
			joinnerVO.setChatRoomId(chatRoom.getChatRoomId());
			chatRoom.setJoinnerList(chatRoomDAO.selectChatRoomJoinner(joinnerVO));
		}
		return chatRoomList;
	}
}
