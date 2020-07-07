package com.seya330.ranchat.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.chat.vo.ChatMessageVO;

@Repository
public class ChatMessageDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.dao.ChatMessageDAO.";
	
	public int insertChatRoomMessage(ChatMessageVO chatMessageVO) {
		return sqlSession.insert(namespace + "insertChatRoomMessage", chatMessageVO);
	}
	
	public int insertChatMessageInfoQuery(ChatMessageVO chatMessageVO) {
		return sqlSession.insert(namespace + "insertChatMessageInfoQuery", chatMessageVO);
	}
	
	public int updateChatMessageInfo(ChatMessageVO chatMessageVO) {
		return sqlSession.update(namespace + "updateChatMessageInfo", chatMessageVO);
	}
}
