package com.seya330.ranchat.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
	private String senderSessionId;
    private String message;
    private MessageType messageType;
    
    public String toString() {
    	return "ChatMessage{" + "senderSessionId='" + senderSessionId + '\'' + ", message='" + message + '\'' + ", messageType=" + messageType + '}';
    }
}
