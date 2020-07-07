package com.seya330.ranchat.chat.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.seya330.ranchat.chat.interfaces.ChatRoom;
import com.seya330.ranchat.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class RandomChatRoomBean implements ChatRoom{
	private String roomId;
	private String name;
	private Map<String, ChatUserBean> joinUserMap;
	
	public RandomChatRoomBean() {
		this.initRoom();
	}

	@Override
	public void initRoom() {
		joinUserMap = new LinkedHashMap<String, ChatUserBean>();
		roomId = IDGeneratorUtil.generateId("R", 19);
	}
}
