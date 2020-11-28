package com.seya330.ranchat.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.chat.vo.TopicChatMessageVO;

@Repository
public class TopicChatMessageDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.chat.dao.TopicChatMessageDAO.";

	public int selectTopicChatMessageCnt(TopicChatMessageVO vo) {
		return sqlSession.selectOne(namespace + "selectTopicChatMessageCnt", vo);
	}
	
	public ArrayList selectTopicChatMessage(TopicChatMessageVO vo) {
		return (ArrayList)sqlSession.selectList(namespace + "selectTopicChatMessage", vo);
	}
	
	public int insertTopicChatMessage(TopicChatMessageVO vo){
		return sqlSession.insert(namespace + "insertTopicChatMessage", vo);
	}
}
