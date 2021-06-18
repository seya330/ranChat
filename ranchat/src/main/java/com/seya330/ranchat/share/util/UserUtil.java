package com.seya330.ranchat.share.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.seya330.ranchat.core.user.vo.RegUserVO;
import com.seya330.ranchat.share.util.ServletUtil;

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
	
	public static String getSha256String(String var) {
		String val = "";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(var.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<byteData.length; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			val = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			val = "";
		}
		return val;
	}
}
