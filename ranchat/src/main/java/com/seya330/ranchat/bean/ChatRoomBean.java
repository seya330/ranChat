package com.seya330.ranchat.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class ChatRoomBean {
	private String roomId;
	private String name;
	private Map<String, ChatUserBean> joinUserMap;
	
	public ChatRoomBean() {
		joinUserMap = new LinkedHashMap<String, ChatUserBean>();
		roomId = UUID.randomUUID().toString();
	}
}
