package com.seya330.ranchat.user.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.user.vo.RegUserVO;

@Repository
public class RegUserDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.dao.RegUserDAO.";
	
	public int insertRegUser(RegUserBean userBean) {
		return sqlSession.insert(namespace + "insertRegUser", userBean);
	}
	
	public int selectRegUserCnt(RegUserVO userVO) {
		return sqlSession.selectOne(namespace + "selectRegUserCnt", userVO);
	}
	
	public RegUserBean selectRegUser(RegUserVO userVO) {
		return sqlSession.selectOne(namespace + "selectRegUser", userVO);
	}
	
}
