package com.seya330.ranchat.infrastructure.chat;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.core.chat.vo.TopicChatRoomVO;
import com.seya330.ranchat.core.chat.vo.TopicRoomVisitVO;

@Repository
public class TopicChatRoomDAO {
	private final String namespace = "com.seya330.ranchat.chat.dao.TopicChatRoomDAO.";
	
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	public ArrayList<TopicChatRoomVO> selectTopicChatRoom(TopicChatRoomVO vo) {
		return (ArrayList) sqlSession.selectList(namespace + "selectTopicChatRoom", vo);
	}
	
	public int insertTopicRoomVisit(TopicRoomVisitVO vo) {
		return sqlSession.insert(namespace + "insertTopicRoomVisit", vo);
	}
	
	public int updateTopicRoomVisit(TopicRoomVisitVO vo) {
		return sqlSession.update(namespace + "updateTopicRoomVisit", vo);
	}
	
	public int selectTopicRoomVisitCnt(TopicRoomVisitVO vo) {
		return sqlSession.selectOne(namespace + "selectTopicRoomVisitCnt", vo);
	}
	
	public ArrayList<TopicRoomVisitVO> selectTopicRoomVisit(TopicRoomVisitVO vo){
		return (ArrayList) sqlSession.selectList(namespace + "selectTopicRoomVisit", vo);
	}
}
