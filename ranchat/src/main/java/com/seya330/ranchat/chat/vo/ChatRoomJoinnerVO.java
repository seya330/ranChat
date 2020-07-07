package com.seya330.ranchat.chat.vo;

import java.sql.Date;

import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomJoinnerVO extends RegUserVO{
	private String chatRoomId;
	private String joinnerId;
	private String joinDate;
	private String joinStatus;
	private String updtDate;
	
	public ChatRoomJoinnerVO() {
		
	}
	
	public ChatRoomJoinnerVO(ChatRoomVO chatRoom, RegUserBean userBean) {
		this.chatRoomId = chatRoom.getChatRoomId();
		this.joinnerId = userBean.getUniqId();
	}
}
