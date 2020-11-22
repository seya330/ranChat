package com.seya330.ranchat.user.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seya330.ranchat.user.dao.RegUserDAO;
import com.seya330.ranchat.user.util.JwtUtil;
import com.seya330.ranchat.user.util.UserUtil;
import com.seya330.ranchat.user.vo.LoginResultType;
import com.seya330.ranchat.user.vo.RegUserVO;
import com.seya330.ranchat.util.IDGeneratorUtil;

@Service
public class UserService {
	@Autowired
	RegUserDAO userDAO;
	
	@Autowired
	JwtUtil jwtUtil;
	
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
	
	public LoginResultType loginToRanchat(RegUserVO userVO) {
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
	
	public RegUserVO getToken(RegUserVO userVO) {
		RegUserVO userBean = null;
		if(userDAO.selectRegUser(userVO).size() > 0)
			userBean = userDAO.selectRegUser(userVO).get(0);
		
		if(userBean == null) {
			userBean = new RegUserVO();
			userBean.setLoginResultType(LoginResultType.INVALID_ID);
		}else if(!userBean.getPassword().equals(userVO.getPassword())) {
			userBean.setLoginResultType(LoginResultType.INVALID_PASSWORD);
		}else {
			userBean.setLoginResultType(LoginResultType.SUCCESS);
			String token = jwtUtil.userBeanToJwtToken(userBean);
			userBean.setToken(token);
		}
		return userBean;
	}
	
	
	public RegUserVO getUserOne(RegUserVO regUserVO) {
		return userDAO.selectRegUser(regUserVO).get(0);
	}
	
	public ArrayList<RegUserVO> searchUser(RegUserVO userVO){
		int cnt = userDAO.selectRegUserCnt(userVO);
		if(cnt == 0)
			return new ArrayList<RegUserVO>();
		return userDAO.selectRegUser(userVO);
	}
}
