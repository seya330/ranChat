package com.seya330.ranchat.core.chat.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.seya330.ranchat.core.chat.interfaces.ChatRoom;
import com.seya330.ranchat.core.user.vo.RegUserVO;
import com.seya330.ranchat.share.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomVO implements ChatRoom {
	private String chatRoomId;
	private String chatRoomName;
	private String summary;
	private RegUserVO regUserVO;
	private Map<String, RegUserVO> joinUserMap;
	private ArrayList<ChatRoomJoinnerVO> joinnerList;
	private int unreadCnt;
	private String[] inviteUniqIdList;
	private String regDate;
	private String msgRegDate;
	
	public ChatRoomVO() {
		joinUserMap = new LinkedHashMap<String, RegUserVO>();
	}
	
	@Override
	public void initRoom() {
		chatRoomId = IDGeneratorUtil.generateId("R", 19);
	}
	
	public void addJoinUser(RegUserVO userBean) {
		joinUserMap.put(userBean.getUniqId(), userBean);
	}
	
	public ArrayList<ChatRoomJoinnerVO> getJoinnerList(){
		if(this.joinnerList == null)
			this.joinnerList = new ArrayList<ChatRoomJoinnerVO>();
		return joinnerList;
	}
}
