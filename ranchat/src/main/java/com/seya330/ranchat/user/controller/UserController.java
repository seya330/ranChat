package com.seya330.ranchat.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seya330.ranchat.user.service.UserService;
import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.user.vo.LoginResultType;
import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/add")
	public Object add(@RequestBody RegUserVO userVO) {
		HttpStatus result = HttpStatus.OK;
		if(!userService.addUser(userVO)) {
			result = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return ResponseEntity.status(result).body(null);
	}
	
	@GetMapping("isOverlap/{userId}")
	public Object isOverlap(@PathVariable String userId) {
		HttpStatus result = HttpStatus.OK;
		RegUserVO userVO = new RegUserVO();
		userVO.setUserId(userId);
		return ResponseEntity.status(result).body(userService.isOverlap(userVO));
	}
	
	@PostMapping("/login")
	public Object login(@RequestBody RegUserVO userVO) {
		HttpStatus result = HttpStatus.OK;
		LoginResultType resultType = userService.login(userVO);
		
		if(resultType != resultType.SUCCESS) {
			result = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return ResponseEntity.status(result).body(resultType);
	}
	
	@GetMapping("/search")
	public Object search(RegUserVO regUserVO) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.searchUser(regUserVO));
	}
}
