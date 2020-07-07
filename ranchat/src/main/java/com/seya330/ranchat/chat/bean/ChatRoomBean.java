package com.seya330.ranchat.chat.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.seya330.ranchat.chat.interfaces.ChatRoom;
import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomBean implements ChatRoom{
	private String roomId;
	private String name;
	private Map<String, RegUserBean> joinUserMap;
	
	@Override
	public void initRoom() {
		// TODO Auto-generated method stub
		joinUserMap = new LinkedHashMap<String, RegUserBean>();
		roomId = IDGeneratorUtil.generateId("R", 19);
	}
}
