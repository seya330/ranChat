package com.seya330.ranchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seya330.ranchat.chat.dao.ChatDAO;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/randomChat")
	public String randomChat() {
		return "chat/randomChat";
	}
	
	@RequestMapping("/chatHomeSample")
	public String chatHome() {
		return "chat/chatHomeSample";
	}
	
	@RequestMapping("/temp")
	public String temp() {
		return "chat/generic";
	}
}
