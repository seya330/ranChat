package com.seya330.ranchat.presentation.api.rest.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
