package com.seya330.ranchat.chat.vo;

import java.util.ArrayList;

import com.seya330.ranchat.common.vo.PagingVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicChatVO {
	TopicChatRoomVO topicChatRoom;
	TopicChatMessageVO topicChatMessage;
	ArrayList<TopicChatRoomVO> topicChatRoomList;
	ArrayList<TopicChatMessageVO> topicChatMessageList;
	ArrayList<TopicRoomVisitVO> topicRoomVisitList;
	PagingVO paging;
	
	public PagingVO getPaging() {
		if(this.paging == null)
			this.paging =  new PagingVO();
		
		return this.paging;
	}
}
