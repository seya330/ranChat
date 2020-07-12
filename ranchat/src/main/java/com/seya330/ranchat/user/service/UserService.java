package com.seya330.ranchat.user.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seya330.ranchat.user.dao.RegUserDAO;
import com.seya330.ranchat.user.util.UserUtil;
import com.seya330.ranchat.user.vo.LoginResultType;
import com.seya330.ranchat.user.vo.RegUserVO;
import com.seya330.ranchat.util.IDGeneratorUtil;

@Service
public class UserService {
	@Autowired
	RegUserDAO userDAO;
	
	public boolean addUser(RegUserVO userVO) {
		RegUserVO userBean = new RegUserVO();
		userBean.setUniqId(IDGeneratorUtil.generateId("M", 19));
		userBean.setUserId(userVO.getUserId());
		userBean.setPassword(userVO.getPassword());
		userDAO.insertRegUser(userBean);
		return true;
	}
	
	public boolean isOverlap(RegUserVO userVO) {
		int a = userDAO.selectRegUserCnt(userVO);
		return userDAO.selectRegUserCnt(userVO) > 0 ? true : false;
	}
	
	public LoginResultType login(RegUserVO userVO) {
		RegUserVO userBean = userDAO.selectRegUser(userVO).get(0);
		if(userBean == null) {			
			return LoginResultType.INVALID_ID;
		}
		
		if(!userBean.getPassword().equals(userVO.getPassword())) {
			//비밀번호 오류
			return LoginResultType.INVALID_PASSWORD;
		}
		
		UserUtil.setUserInSession(userBean);
		return LoginResultType.SUCCESS;
		
	}
	
	public RegUserVO getUserOne(RegUserVO regUserVO) {
		return userDAO.selectRegUser(regUserVO).get(0);
	}
	
	public ArrayList<RegUserVO> searchUser(RegUserVO userVO){
		userVO.getSearchVO().addExceptUniqId(UserUtil.getUserInSession().getUniqId());
		int cnt = userDAO.selectRegUserCnt(userVO);
		if(cnt == 0)
			return new ArrayList<RegUserVO>();
		return userDAO.selectRegUser(userVO);
	}
}
