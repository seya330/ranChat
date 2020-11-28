package com.seya330.ranchat.chat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.seya330.ranchat.chat.service.TopicChatService;
import com.seya330.ranchat.chat.vo.TopicChatMessageVO;
import com.seya330.ranchat.chat.vo.TopicChatRoomVO;
import com.seya330.ranchat.chat.vo.TopicChatVO;
import com.seya330.ranchat.chat.vo.TopicRoomVisitVO;
import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.user.vo.RegUserVO;

@RestController
@RequestMapping("/topic")
public class TopicController {
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	TopicChatService topicChatService;
	
	@GetMapping("/search/{chatTopic}")
	public ResponseEntity topicSearch(@PathVariable String chatTopic) {
		TopicChatRoomVO vo = new TopicChatRoomVO();
		vo.setChatTopic(chatTopic);
		return ResponseEntity.status(HttpStatus.OK).body(topicChatService.searchTopicChatRoom(vo));
	}
	
	@GetMapping("/chatList")
	public ResponseEntity topicChatList(HttpServletRequest request, TopicChatMessageVO messageVO) {
		String token = request.getHeader("authToken");
		RegUserVO user = jwtUtil.getUserByToken(token);
		
		TopicChatVO vo = new TopicChatVO();
		messageVO.setRegUserVO(user);
		vo.setTopicChatMessageList(topicChatService.getTopicChatMessageList(messageVO));
		vo.setPaging(messageVO.getPagingVO());
		return ResponseEntity.status(HttpStatus.OK).body(vo);
	}
	
	@GetMapping("/visitList")
	public ResponseEntity topicVisitList(HttpServletRequest request, TopicRoomVisitVO visitVO) {
		String token = request.getHeader("authToken");
		RegUserVO user = jwtUtil.getUserByToken(token);
		
		visitVO.setVisitor(user.getUniqId());
		
		TopicChatVO vo = new TopicChatVO();
		vo.setTopicRoomVisitList(topicChatService.getVisitList(visitVO));
		return ResponseEntity.status(HttpStatus.OK).body(vo);
	}
}
