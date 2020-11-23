package com.seya330.ranchat.chat.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import com.seya330.ranchat.chat.dao.ChatRoomDAO;
import com.seya330.ranchat.chat.vo.ChatMessageVO;
import com.seya330.ranchat.chat.vo.ChatResponse;
import com.seya330.ranchat.chat.vo.ChatRoomJoinnerVO;
import com.seya330.ranchat.chat.vo.MessageType;
import com.seya330.ranchat.chat.vo.ChatResponse.ResponseResult;
import com.seya330.ranchat.chat.vo.ChatRoomVO;
import com.seya330.ranchat.chat.vo.GroupChatResult;
import com.seya330.ranchat.chat.vo.GroupChatVO;
import com.seya330.ranchat.user.service.UserService;
import com.seya330.ranchat.user.util.UserUtil;
import com.seya330.ranchat.user.vo.RegUserVO;

@Service
public class ChatService {
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
	private Map<String, DeferredResult<ChatResponse>> waitingUsers;
	private ReentrantReadWriteLock lock;
	private Map<String, Map> connectedUsers;
	
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatRoomDAO chatRoomDAO;
	
	@PostConstruct
	private void setUp() {
		waitingUsers = new LinkedHashMap<>();
		this.lock = new ReentrantReadWriteLock();
		connectedUsers = new LinkedHashMap<String, Map>();
	}
	
	//비동기 작동 할 수 있도록 구현
	@Async("asyncThreadPool")
    public void joinChatRoom(String userSessionId, DeferredResult<ChatResponse> deferredResult) {
        logger.info("## Join chat room request. {}[{}]", Thread.currentThread().getName(), Thread.currentThread().getId());
        if (userSessionId == null || deferredResult == null) {
            return;
        }

        try {
        	lock.writeLock().lock();
        	waitingUsers.put(userSessionId, deferredResult);
        }finally {
        	lock.writeLock().unlock();
        	establishChatRoomToRandom();
        }
    }
	
	/**
	 * 두명의 참가자가 접속하여 방 생성
	 * */
    public void establishChatRoomToRandom() {
        try {
            logger.info("Current waiting users : " + waitingUsers.size());
            lock.readLock().lock();
            if (waitingUsers.size() < 2) {
                return;
            }

            Iterator<String> itr = waitingUsers.keySet().iterator();
            String user1 = itr.next();
            String user2 = itr.next();

            String uuid = UUID.randomUUID().toString();

            DeferredResult<ChatResponse> user1Result = waitingUsers.remove(user1);
            DeferredResult<ChatResponse> user2Result = waitingUsers.remove(user2);

            user1Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user1));
            user2Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user2));
        } catch (Exception e) {
            logger.warn("Exception occur while checking waiting users", e);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void timeoutWaitingUser(String sessionId) {
    	logger.info("timeout session id : " + sessionId);
    	lock.readLock().lock();
    	waitingUsers.remove(sessionId);
    	logger.info("Current waiting users : " + waitingUsers.size());
    	lock.readLock().unlock();
    }
    
    public void cancelWaiting(String sessionId) {
    	logger.info("waiting cancel: " + sessionId);
    	lock.writeLock().lock();
    	DeferredResult<ChatResponse> result = waitingUsers.remove(sessionId);
    	result.setResult(new ChatResponse(ResponseResult.CANCEL, sessionId));
    	lock.writeLock().unlock();
    	logger.info("Current waiting users : " + waitingUsers.size());
    }
    
    public void sendMessage(String chatRoomId, ChatMessageVO chatMessage) {
        String destination = getDestination(chatRoomId);
        messagingTemplate.convertAndSend(destination, chatMessage);
    }
    
    private String getDestination(String chatRoomId) {
        return "/sub/chat/" + chatRoomId;
    }
    
    public void connectUser(String websocketSessionId, Map chatRoomId) {
        connectedUsers.put(websocketSessionId, chatRoomId);
    }
    
    public void disconnectUser(String websocketSessionId) {
    	Map resultMap = connectedUsers.remove(websocketSessionId);
    	waitingUsers.remove(websocketSessionId);
    	ChatMessageVO chatMessage = new ChatMessageVO();
    	
    	chatMessage.setMessageType(MessageType.DISCONNECTED);
    	sendMessage((String)resultMap.get("chatRoomId"), chatMessage);
    }
    
    //유저정보 가지고 옴 > 유저가 없으면 잘못된 id! > 유저가 있으면 방 id값이 있는지 확인> 방이 이미 존재하는 방이면 이미 그 방에 초대되어 있는지 확인 >방이 이미 존재하지 않으면 신규 생성 > 초대 안되어 있으면 초대 > 초대 되어 있으면 이미초대됨!
    @Transactional
    public GroupChatVO inviteUser(ChatRoomVO chatRoomVO) {
    	GroupChatVO result = new GroupChatVO();
    	if(chatRoomVO.getInviteUniqIdList() == null || chatRoomVO.getInviteUniqIdList().length == 0) {
    		result.setResult(GroupChatResult.EMPTY);
    		return result;
    	}
    	
    	result.setResult(GroupChatResult.SUCCESS);
    	
    	//채팅방 신규 생성
    	ChatRoomVO room = new ChatRoomVO();
    	room.initRoom();
    	chatRoomDAO.insertChatRoom(room);
    	
    	//자신을 채팅방 접속자에 추가
    	ChatRoomJoinnerVO joinner = new ChatRoomJoinnerVO(room, chatRoomVO.getRegUserVO());
    	chatRoomDAO.insertChatRoomJoinner(joinner);
    	room.addJoinUser(chatRoomVO.getRegUserVO());
    	
    	//자신 외 사용자들 추가
    	for(String uniqId : chatRoomVO.getInviteUniqIdList()) {
    		RegUserVO param = new RegUserVO();
    		param.setUniqId(uniqId);
    		RegUserVO userVO = userService.getUserOne(param);
    		if(userVO == null) {
    			result.setResult(GroupChatResult.INVALID_USER_ID);
    			return result;
    		}
    		
    		if(userVO.getUniqId().equals(chatRoomVO.getRegUserVO().getUniqId())) {
        		result.setResult(GroupChatResult.SAME_USER_ID);
        		return result;
        	}
    		
    		ChatRoomJoinnerVO joinner2 = new ChatRoomJoinnerVO(room, userVO);
        	chatRoomDAO.insertChatRoomJoinner(joinner2);
        	room.addJoinUser(userVO);
    	}
    	
    	result.setChatRoomVO(room);
    	return result;
    }
    
}
