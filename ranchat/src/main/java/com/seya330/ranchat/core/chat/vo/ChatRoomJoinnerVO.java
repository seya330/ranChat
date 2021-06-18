package com.seya330.ranchat.core.chat.vo;

import com.seya330.ranchat.core.user.vo.RegUserVO;

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
	
	public ChatRoomJoinnerVO(ChatRoomVO chatRoom, RegUserVO userBean) {
		this.chatRoomId = chatRoom.getChatRoomId();
		this.joinnerId = userBean.getUniqId();
	}
}
