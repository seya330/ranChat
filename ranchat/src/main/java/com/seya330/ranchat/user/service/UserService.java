package com.seya330.ranchat.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seya330.ranchat.user.bean.RegUserBean;
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
		RegUserBean userBean = new RegUserBean();
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
		RegUserBean userBean = userDAO.selectRegUser(userVO);
		if(userBean == null) {			
			return LoginResultType.INVALID_ID;
		}
		
		if(!userBean.getPassword().equals(userVO.getPassword())) {
			//비밀번호 오류
			return LoginResultType.INVALID_PASSWORD;
		}
		
		userVO.setRegUserBean(userBean);
		UserUtil.setUserInSession(userVO.getRegUserBean());
		return LoginResultType.SUCCESS;
		
	}
	
	public RegUserBean getUserByUserId(String userId) {
		RegUserVO vo = new RegUserVO();
		vo.setUserId(userId);
		return userDAO.selectRegUser(vo);
	}
}
