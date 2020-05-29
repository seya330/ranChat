package com.seya330.ranchat.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;
@Repository
public class ChatRoomRepository {
	private Map<String, ChatRoomBean> chatRoomMap;
	
	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<String, ChatRoomBean>();
	}
	
	public List<ChatRoomBean> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }
    public ChatRoomBean findRoomById(String id) {
        return chatRoomMap.get(id);
    }
    public ChatRoomBean createChatRoom() {
        ChatRoomBean chatRoom = new ChatRoomBean();
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
    
    public ChatRoomBean removeRoom(String roomId) {
    	return (ChatRoomBean)chatRoomMap.remove("roomId");
    }
}
