package com.seya330.ranchat.core.chat.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import com.seya330.ranchat.core.chat.interfaces.ChatRoom;
import com.seya330.ranchat.core.user.vo.RegUserVO;
import com.seya330.ranchat.share.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomBean implements ChatRoom {
	private String roomId;
	private String name;
	private Map<String, RegUserVO> joinUserMap;
	
	@Override
	public void initRoom() {
		// TODO Auto-generated method stub
		joinUserMap = new LinkedHashMap<String, RegUserVO>();
		roomId = IDGeneratorUtil.generateId("R", 19);
	}
}
