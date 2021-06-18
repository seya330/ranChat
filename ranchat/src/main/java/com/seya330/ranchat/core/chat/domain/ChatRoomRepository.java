package com.seya330.ranchat.core.chat.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;
@Repository
public class ChatRoomRepository {
	private Map<String, RandomChatRoomBean> chatRoomMap;
	
	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<String, RandomChatRoomBean>();
	}
	
	public List<RandomChatRoomBean> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }
    public RandomChatRoomBean findRoomById(String id) {
        return chatRoomMap.get(id);
    }
    public RandomChatRoomBean createChatRoom() {
        RandomChatRoomBean chatRoom = new RandomChatRoomBean();
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
    
    public RandomChatRoomBean removeRoom(String roomId) {
    	return (RandomChatRoomBean)chatRoomMap.remove("roomId");
    }
}
