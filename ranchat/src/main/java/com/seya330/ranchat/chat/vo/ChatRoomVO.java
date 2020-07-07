package com.seya330.ranchat.chat.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.seya330.ranchat.chat.interfaces.ChatRoom;
import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.util.IDGeneratorUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomVO implements ChatRoom{
	private String chatRoomId;
	private String chatRoomName;
	private String summary;
	private RegUserBean regUserBean;
	private Map<String, RegUserBean> joinUserMap;
	private ArrayList<ChatRoomJoinnerVO> joinnerList;
	private int unreadCnt;
	
	public ChatRoomVO() {
		joinUserMap = new LinkedHashMap<String, RegUserBean>();
	}
	
	@Override
	public void initRoom() {
		chatRoomId = IDGeneratorUtil.generateId("R", 19);
	}
	
	public void addJoinUser(RegUserBean userBean) {
		joinUserMap.put(userBean.getUniqId(), userBean);
	}
	
	public ArrayList<ChatRoomJoinnerVO> getJoinnerList(){
		if(this.joinnerList == null)
			this.joinnerList = new ArrayList<ChatRoomJoinnerVO>();
		return joinnerList;
	}
}
