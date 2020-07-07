package com.seya330.ranchat.user.vo;

import java.sql.Date;

import com.seya330.ranchat.user.bean.RegUserBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegUserVO {
	String uniqId;
	String userId;
	String password;
	Date regDate;
	Date modDate;
	String userStatus;
	RegUserBean regUserBean;
	LoginResultType loginResultType;
	
	public String getUserStatus() {
		if(userStatus == null || "".equals(userStatus))
			return "U";
		else
			return userStatus;
	}
}
