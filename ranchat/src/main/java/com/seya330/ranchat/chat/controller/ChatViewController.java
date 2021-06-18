package com.seya330.ranchat.chat.controller;

import com.seya330.ranchat.user.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/view/chat")
public class ChatViewController {
  private static final Logger logger = LoggerFactory.getLogger(ChatViewController.class);
  @Autowired
  JwtUtil jwtUtil;

  @GetMapping("/groupChat")
  public String roomChat(HttpServletRequest request) {
    return "chat/groupChat";
  }

  @GetMapping("/test")
  public String test(HttpServletRequest request) {
    return "chat/mobile/vueTest";
  }
}
