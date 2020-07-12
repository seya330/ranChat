package com.seya330.ranchat.user.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.seya330.ranchat.user.vo.RegUserVO;
import com.seya330.ranchat.util.ServletUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserUtil {
	private static final String LOGIN_SESSION = "LOGIN_SESSION";
	
	public static void setUserInSession(RegUserVO userVO) {
		ServletUtil.getSession().setAttribute(LOGIN_SESSION, userVO);
	}
	
	public static RegUserVO getUserInSession() {
		return (RegUserVO)ServletUtil.getSession().getAttribute(LOGIN_SESSION);
	}
	
	public static void removeUserOfSession() {
		ServletUtil.getSession().removeAttribute("LOGIN_SESSION");
	}
	
}
