package com.seya330.ranchat.chat.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.seya330.ranchat.chat.bean.ChatUserBean;
import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageVO {
	//randomChat 영역
	private String senderSessionId;
    private String message;
    private MessageType messageType;
    private ChatUserBean chatUserBean;
    private String sendUserName;
    private Date sendDate;
    
    //groupChat 영역
    private String messageId;
    private String messageContents;
    private String senderId;
    private String sendUserId;
    private String regDate;
    private String chatRoomId;
    private String readYn;
    private String receiverId;
    
    private RegUserVO sender;
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
