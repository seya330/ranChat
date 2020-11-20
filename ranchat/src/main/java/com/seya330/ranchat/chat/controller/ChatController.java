package com.seya330.ranchat.chat.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.seya330.ranchat.chat.bean.ChatRoomRepository;
import com.seya330.ranchat.chat.bean.ChatUserBean;
import com.seya330.ranchat.chat.service.ChatMessageService;
import com.seya330.ranchat.chat.service.ChatRoomService;
import com.seya330.ranchat.chat.service.ChatService;
import com.seya330.ranchat.chat.vo.ChatMessageVO;
import com.seya330.ranchat.chat.vo.ChatResponse;
import com.seya330.ranchat.chat.vo.ChatRoomVO;
import com.seya330.ranchat.user.service.UserService;
import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.user.util.UserUtil;
import com.seya330.ranchat.user.vo.RegUserVO;
import com.seya330.ranchat.util.ServletUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class); 
	
	@Autowired
	ChatRoomRepository roomRepo;
	
	@Autowired
	SimpMessagingTemplate simpleTemplate;
	
	@Autowired
	SimpMessageSendingOperations simpleOperation; 
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@PostMapping("/invite")
	public Object invite(@RequestBody ChatRoomVO chatRoomVO) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("auth-token");
		
		return ResponseEntity.status(HttpStatus.OK).body(chatService.inviteUser(chatRoomVO));
	}
	
	@PostMapping("/groupChat/createToken")
	public Object createToken(HttpServletResponse response) {
		RegUserVO userBean = UserUtil.getUserInSession();
		HashMap<String, String> result = new HashMap<String, String>();
		String jwtToken = jwtUtil.userBeanToJwtToken(userBean);
		
		result.put("token", jwtToken);
		result.put("uniqId", userBean.getUniqId());
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/join")
	public DeferredResult<ChatResponse> joinRequest(ChatUserBean chatUserBean){
		String sessionId = ServletUtil.getSession().getId();
		logger.info("join request. session id : {}, join user name : {}", sessionId, chatUserBean.getUserName());
		
		final DeferredResult<ChatResponse> deferredResult = new DeferredResult<>(null);
		chatService.joinChatRoom(sessionId, deferredResult);
		return deferredResult;
	}
	
	@GetMapping("/groupChat/chatRoomList")
	public Object chatRoomList(HttpServletRequest request) {
		//토큰 가지고 와서 chatRoomVO 에 RegUserVO 에 셋팅 해주고 해야함
		String token = request.getHeader("authToken");
		RegUserVO user = jwtUtil.getUserByToken(token);
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setRegUserVO(user);
		return ResponseEntity.status(HttpStatus.OK).body(chatRoomService.getChatRoomList(chatRoomVO));
	}
	
	@GetMapping("/groupChat/chatList")
	public Object chatList(HttpServletRequest request, ChatMessageVO chatMessageVO) {
		String token = request.getHeader("authToken");
		String asdf = request.getParameter("chatRoomId");
		RegUserVO user = jwtUtil.getUserByToken(token);
		chatMessageVO.setReceiverId(user.getUniqId());
		chatMessageVO.setMessageList(chatMessageService.getChatMessageList(chatMessageVO));
		return ResponseEntity.status(HttpStatus.OK).body(chatMessageVO);
	}
	
	@GetMapping("/groupChat/message/view")
	public Object messageView(ChatMessageVO chatMessageVO) {
		chatMessageVO.setReceiverId(UserUtil.getUserInSession().getUniqId());
		chatMessageService.readChatMessage(chatMessageVO);
		return ResponseEntity.status(HttpStatus.OK).body("");
	}
}
