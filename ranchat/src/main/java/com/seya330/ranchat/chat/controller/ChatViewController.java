package com.seya330.ranchat.chat.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.user.util.UserUtil;

@Controller
@RequestMapping("/view/chat")
public class ChatViewController {
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/groupChat")
	public String roomChat() {
		return "chat/groupChat";
	}
}
