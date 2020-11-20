package com.seya330.ranchat.chat.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seya330.ranchat.user.util.JwtUtil;

@Controller
@RequestMapping("/view/chat")
public class ChatViewController {
	private static final Logger logger = LoggerFactory.getLogger(ChatViewController.class); 
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/groupChat")
	public String roomChat(HttpServletRequest request) {
		Device device = DeviceUtils.getCurrentDevice(request);
		if(device.isMobile()) {
			return "chat/mobile/groupChat";
		}else if(device.isTablet()) {
			return "chat/mobile/groupChat";
		}else {
			return "chat/groupChat";
		}
	}
	
	@GetMapping("/test")
	public String test(HttpServletRequest request) {
		return "chat/mobile/vueTest";
	}
}
