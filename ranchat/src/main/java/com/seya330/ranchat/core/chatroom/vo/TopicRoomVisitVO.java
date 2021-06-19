package com.seya330.ranchat.core.chatroom.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicRoomVisitVO {
	String visitor;
	String chatRoomId;
	String visitDate;
	String chatTopic;
	int visitCnt;
}
