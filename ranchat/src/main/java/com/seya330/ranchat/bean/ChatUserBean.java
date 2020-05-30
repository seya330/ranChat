package com.seya330.ranchat.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatUserBean {
	private String userName;
	private String sessionId;
	private Date connectDate;
	
	public ChatUserBean() {}
	public ChatUserBean(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
