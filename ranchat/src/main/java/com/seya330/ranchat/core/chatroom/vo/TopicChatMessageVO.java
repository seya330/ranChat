package com.seya330.ranchat.core.chatroom.vo;

import com.seya330.ranchat.share.vo.PagingVO;
import com.seya330.ranchat.core.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TopicChatMessageVO {
	String messageId;
	String messageContents;
	String senderId;
	String chatRoomId;
	String messageStatus;
	String regDate;
	RegUserVO regUserVO;
	PagingVO pagingVO;
	String sendUserId;
	boolean init;
	
	public PagingVO getPagingVO() {
		if(this.pagingVO == null)
			this.pagingVO =  new PagingVO();
		
		return this.pagingVO;
	}
}
