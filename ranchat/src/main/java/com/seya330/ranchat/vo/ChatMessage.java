package com.seya330.ranchat.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.seya330.ranchat.bean.ChatUserBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
	private String senderSessionId;
    private String message;
    private MessageType messageType;
    private ChatUserBean chatUserBean;
    private String sendUserName;
    private Date sendDate;
    
    public String toString() {
    	return "ChatMessage{" + "senderSessionId='" + senderSessionId + '\'' + ", message='" + message + '\'' + ", messageType=" + messageType + '}';
    }
    
    public Date getSendDate() {
    	if(this.sendDate == null) {
    		return new Date();
    	}
    	return sendDate;
    }
    
    public String getSendDateString() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return format.format(getSendDate());
    }
}
