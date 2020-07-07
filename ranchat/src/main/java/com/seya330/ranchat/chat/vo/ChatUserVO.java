package com.seya330.ranchat.chat.vo;

import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatUserVO extends RegUserVO{
	String chatRoomId;
}
