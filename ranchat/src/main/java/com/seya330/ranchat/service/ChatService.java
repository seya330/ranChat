package com.seya330.ranchat.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.seya330.ranchat.bean.ChatUserBean;
import com.seya330.ranchat.vo.ChatMessage;
import com.seya330.ranchat.vo.ChatResponse;
import com.seya330.ranchat.vo.MessageType;
import com.seya330.ranchat.vo.ChatResponse.ResponseResult;

@Service
public class ChatService {
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
	private Map<ChatUserBean, DeferredResult<ChatResponse>> waitingUsers;
	private ReentrantReadWriteLock lock;
	private Map<String, String> connectedUsers;
	
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	
	@PostConstruct
	private void setUp() {
		waitingUsers = new LinkedHashMap<>();
		this.lock = new ReentrantReadWriteLock();
		connectedUsers = new LinkedHashMap<String, String>();
	}
	
	@Async("asyncThreadPool")
    public void joinChatRoom(ChatUserBean user, DeferredResult<ChatResponse> deferredResult) {
        logger.info("## Join chat room request. {}[{}]", Thread.currentThread().getName(), Thread.currentThread().getId());
        if (user == null || deferredResult == null) {
            return;
        }

        try {
        	lock.writeLock().lock();
        	waitingUsers.put(user, deferredResult);
        }finally {
        	lock.writeLock().unlock();
        	establishChatRoom();
        }
    }
	
	/**
	 * 두명의 참가자가 접속하여 방 생성
	 * */
    public void establishChatRoom() {
        try {
            logger.info("Current waiting users : " + waitingUsers.size());
            lock.readLock().lock();
            if (waitingUsers.size() < 2) {
                return;
            }

            Iterator<ChatUserBean> itr = waitingUsers.keySet().iterator();
            ChatUserBean user1 = itr.next();
            ChatUserBean user2 = itr.next();

            String uuid = UUID.randomUUID().toString();

            DeferredResult<ChatResponse> user1Result = waitingUsers.remove(user1);
            DeferredResult<ChatResponse> user2Result = waitingUsers.remove(user2);

            user1Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user1.getSessionId()));
            user2Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user2.getSessionId()));
        } catch (Exception e) {
            logger.warn("Exception occur while checking waiting users", e);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void sendMessage(String chatRoomId, ChatMessage chatMessage) {
        String destination = getDestination(chatRoomId);
        messagingTemplate.convertAndSend(destination, chatMessage);
    }
    
    private String getDestination(String chatRoomId) {
        return "/sub/chat/" + chatRoomId;
    }
    
    public void connectUser(String chatRoomId, String websocketSessionId) {
        connectedUsers.put(websocketSessionId, chatRoomId);
    }
    
    public void disconnectUser(String websocketSessionId) {
    	String chatRoomId = connectedUsers.get(websocketSessionId);
    	ChatMessage chatMessage = new ChatMessage();
    	
    	chatMessage.setMessageType(MessageType.DISCONNECTED);
    	sendMessage(chatRoomId, chatMessage);
    }
}
