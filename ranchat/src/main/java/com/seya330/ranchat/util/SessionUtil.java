package com.seya330.ranchat.util;

import javax.servlet.http.HttpSession;

import com.seya330.ranchat.bean.ChatUserBean;

public class SessionUtil extends ServletUtil{
	public static final String LOGIN_SESSION = "LOGIN_SESSION";
	
	public static ChatUserBean getUser() {
		HttpSession session = getSession();
		return (ChatUserBean)(getSession().getAttribute(LOGIN_SESSION));
	}
	
	public static void setUser(ChatUserBean chatUserBean) {
		getSession().setAttribute(LOGIN_SESSION, chatUserBean);
	}
}
