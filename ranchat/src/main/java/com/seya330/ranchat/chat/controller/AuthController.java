package com.seya330.ranchat.chat.controller;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seya330.ranchat.user.service.UserService;
import com.seya330.ranchat.user.vo.LoginResultType;
import com.seya330.ranchat.user.vo.RegUserVO;

@RestController
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class); 
	@Autowired
	UserService userService;
	
	@CrossOrigin
	@PostMapping("/login")
	public Object login(@RequestBody RegUserVO userVO, HttpServletResponse response) {
		logger.info("login request user: " + userVO.getUserId());
		HttpStatus result = HttpStatus.OK;
		String resultStr = "";
		RegUserVO user = userService.getToken(userVO);
		
		if(user.getLoginResultType() != LoginResultType.SUCCESS) {
			result = HttpStatus.UNAUTHORIZED;
			resultStr = user.getLoginResultType().toString();
		}else {
			resultStr = user.getToken();
		}
		
		return ResponseEntity.status(result).body(user);
	}
	
	@GetMapping("/login2")
	public Object login2(RegUserVO userVO, HttpServletResponse response) {
		logger.info("login request user: " + userVO.getUserId());
		HttpStatus result = HttpStatus.OK;
		String resultStr = "";
		RegUserVO user = userService.getToken(userVO);
		
		if(user.getLoginResultType() != LoginResultType.SUCCESS) {
			result = HttpStatus.UNAUTHORIZED;
			resultStr = user.getLoginResultType().toString();
		}else {
			resultStr = user.getToken();
		}
		
		return ResponseEntity.status(result).body(resultStr);
	}
}
