package com.seya330.ranchat.chat.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatResponse {
	private ResponseResult responseResult;
	private String chatRoomId;
	private String sessionId;
	
	public ChatResponse() {
		
	}
	
	public ChatResponse(ResponseResult responseResult, String sessionId) {
		this.responseResult = responseResult;
		this.sessionId = sessionId;
	}
	
	public ChatResponse(ResponseResult responseResult, String chatRoomId, String sessionId) {
		this.responseResult = responseResult;
		this.chatRoomId = chatRoomId;
		this.sessionId = sessionId;
	}
	
	public enum ResponseResult{
		SUCCESS, CANCEL, TIMEOUT;
	}
}
