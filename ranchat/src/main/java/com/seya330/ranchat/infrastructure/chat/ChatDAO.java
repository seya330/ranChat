package com.seya330.ranchat.infrastructure.chat;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.core.chat.vo.ChatMessageVO;

@Repository
public class ChatDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.dao.ChatDAO.";
	
	public ArrayList selectChatMessage(ChatMessageVO chatMessageVO) {
		return (ArrayList)sqlSession.selectList(namespace + "selectChatMessage", chatMessageVO);
	}
	
	public int selectChatMessageCnt(ChatMessageVO chatMessageVO) {
		return sqlSession.selectOne(namespace + "selectChatMessageCnt", chatMessageVO);
	}
}
