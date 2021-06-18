package com.seya330.ranchat.core.chat.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import com.seya330.ranchat.core.chat.interfaces.ChatRoom;
import com.seya330.ranchat.share.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class RandomChatRoomBean implements ChatRoom {
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
