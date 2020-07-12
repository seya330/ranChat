package com.seya330.ranchat.user.vo;

import java.sql.Date;


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
	LoginResultType loginResultType;
	
	RegUserSearchVO searchVO;
	
	public String getUserStatus() {
		if(userStatus == null || "".equals(userStatus))
			return "U";
		else
			return userStatus;
	}
	
	public RegUserSearchVO getSearchVO() {
		if(searchVO == null)
			searchVO = new RegUserSearchVO();
		
		return searchVO;
	}
}
