package com.seya330.ranchat.core.chatroom.domain;

import com.seya330.ranchat.core.user.vo.RegUserVO;
import com.seya330.ranchat.share.util.IDGeneratorUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class ChatRoom {
	private String roomId;
	private String name;
	private Map<String, RegUserVO> joinUserMap;
	
	public void initRoom() {
		joinUserMap = new LinkedHashMap<String, RegUserVO>();
		roomId = IDGeneratorUtil.generateId("R", 19);
	}
}
