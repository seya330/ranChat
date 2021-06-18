package com.seya330.ranchat.core.chat.vo;

import com.seya330.ranchat.core.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatUserVO extends RegUserVO{
	String chatRoomId;
}
