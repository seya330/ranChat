package com.seya330.ranchat.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRequest {
	private String sessionId;
	public ChatRequest() {
		
	}
	public ChatRequest(String sessionId) {
		this.sessionId = sessionId;
	}
}
