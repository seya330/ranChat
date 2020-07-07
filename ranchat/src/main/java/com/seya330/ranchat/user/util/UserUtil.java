package com.seya330.ranchat.user.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.util.ServletUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserUtil {
	private static final String LOGIN_SESSION = "LOGIN_SESSION";
	
	public static void setUserInSession(RegUserBean userBean) {
		ServletUtil.getSession().setAttribute(LOGIN_SESSION, userBean);
	}
	
	public static RegUserBean getUserInSession() {
		return (RegUserBean)ServletUtil.getSession().getAttribute(LOGIN_SESSION);
	}
	
	public static void removeUserOfSession() {
		ServletUtil.getSession().removeAttribute("LOGIN_SESSION");
	}
	
}
