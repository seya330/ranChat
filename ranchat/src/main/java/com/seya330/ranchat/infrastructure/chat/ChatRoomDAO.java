package com.seya330.ranchat.infrastructure.chat;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.core.chatroom.vo.ChatRoomJoinnerVO;
import com.seya330.ranchat.core.chatroom.vo.ChatRoomVO;

@Repository
public class ChatRoomDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.dao.ChatRoomDAO.";
	
	public int insertChatRoom(ChatRoomVO roomVO) {
		return sqlSession.insert(namespace + "insertChatRoom", roomVO);
	}
	
	public int insertChatRoomJoinner(ChatRoomJoinnerVO joinner) {
		return sqlSession.insert(namespace + "insertChatRoomJoinner", joinner);
	}
	
	public ArrayList<ChatRoomVO> selectChatRoom(ChatRoomVO chatRoomVO) {
		return (ArrayList)sqlSession.selectList(namespace + "selectChatRoom", chatRoomVO);
	}
	
	public ArrayList<ChatRoomJoinnerVO> selectChatRoomJoinner(ChatRoomJoinnerVO joinnerVO){
		return (ArrayList)sqlSession.selectList(namespace + "selectChatRoomJoinner", joinnerVO);
	}
}
