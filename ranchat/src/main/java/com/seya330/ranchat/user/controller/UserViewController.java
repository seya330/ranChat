package com.seya330.ranchat.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seya330.ranchat.user.util.UserUtil;

@Controller
@RequestMapping("/view/user")
public class UserViewController {
	@RequestMapping("/loginView")
	public String loginView(HttpServletRequest request, HttpServletResponse response) {
		return "user/loginView";
	}
	
	@RequestMapping("/add")
	public String addUserView(HttpServletRequest request, HttpServletResponse response) {
		return "user/addUserView";
	}
	
	@GetMapping("/logout")
	public String logout() {
		UserUtil.removeUserOfSession();
		return "redirect:/";
	}
}
