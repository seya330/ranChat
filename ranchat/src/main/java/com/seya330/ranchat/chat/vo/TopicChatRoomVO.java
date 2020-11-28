package com.seya330.ranchat.chat.vo;

import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TopicChatRoomVO {
	String chatRoomId;
	String chatTopic;
	String regUser;
	String regDate;
	RegUserVO regUserVO;
}
