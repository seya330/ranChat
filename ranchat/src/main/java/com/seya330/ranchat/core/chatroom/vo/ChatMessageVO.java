package com.seya330.ranchat.core.chatroom.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.seya330.ranchat.core.chatroom.domain.ChatUserBean;
import com.seya330.ranchat.share.vo.PagingVO;
import com.seya330.ranchat.core.user.vo.RegUserVO;

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
    
    private ArrayList<ChatMessageVO> messageList;
    
    private PagingVO pagingVO;
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
    
    public PagingVO getPagingVO() {
    	if(this.pagingVO == null)
    		pagingVO = new PagingVO();
    	
    	return pagingVO;
    }
}
