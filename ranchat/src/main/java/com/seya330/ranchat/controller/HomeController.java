package com.seya330.ranchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/index2")
	public String home2() {
		return "index2";
	}
	
	@RequestMapping("/chatHomeSample")
	public String chatHome() {
		return "chat/chatHomeSample";
	}
}
