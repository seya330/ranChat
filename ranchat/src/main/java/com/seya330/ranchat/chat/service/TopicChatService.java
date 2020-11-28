package com.seya330.ranchat.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seya330.ranchat.chat.dao.TopicChatMessageDAO;
import com.seya330.ranchat.chat.dao.TopicChatRoomDAO;
import com.seya330.ranchat.chat.vo.TopicChatMessageVO;
import com.seya330.ranchat.chat.vo.TopicChatRoomVO;
import com.seya330.ranchat.chat.vo.TopicChatVO;
import com.seya330.ranchat.chat.vo.TopicRoomVisitVO;
import com.seya330.ranchat.util.IDGeneratorUtil;

@Service
public class TopicChatService {
	@Autowired
	TopicChatRoomDAO topicChatRoomDAO;
	
	@Autowired
	TopicChatMessageDAO topicChatMessageDAO;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	public ArrayList searchTopicChatRoom(TopicChatRoomVO vo) {
		return topicChatRoomDAO.selectTopicChatRoom(vo);
	}
	
	@Transactional
	public ArrayList<TopicChatMessageVO> getTopicChatMessageList(TopicChatMessageVO vo){
		if(vo.isInit()) {
			TopicRoomVisitVO visit = new TopicRoomVisitVO();
			visit.setChatRoomId(vo.getChatRoomId());
			visit.setVisitor(vo.getRegUserVO().getUniqId());
			int visitCnt = topicChatRoomDAO.selectTopicRoomVisitCnt(visit); 
			if(visitCnt >0) {
				topicChatRoomDAO.updateTopicRoomVisit(visit);
			}
			else
				topicChatRoomDAO.insertTopicRoomVisit(visit);
		}
		
		vo.getPagingVO().setTotalCnt(topicChatMessageDAO.selectTopicChatMessageCnt(vo));
		if(vo.getPagingVO().getTotalCnt() == 0)
			return new ArrayList<TopicChatMessageVO>();
		
		return topicChatMessageDAO.selectTopicChatMessage(vo);
	}
	
	public void sendTopicMessage(TopicChatMessageVO message) {
		message.setMessageId(IDGeneratorUtil.generateId("M", 19));
		topicChatMessageDAO.insertTopicChatMessage(message);
		messagingTemplate.convertAndSend("/sub/public."+message.getChatRoomId()+".message", message);
	}
	
	public ArrayList<TopicRoomVisitVO> getVisitList(TopicRoomVisitVO vo){
		return topicChatRoomDAO.selectTopicRoomVisit(vo);
	}
	
}
