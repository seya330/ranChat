package com.seya330.ranchat.user.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.seya330.ranchat.user.vo.RegUserVO;

@Repository
public class RegUserDAO {
	@Autowired
	@Qualifier("defaultSqlSession")
	SqlSession sqlSession;
	
	private final String namespace = "com.seya330.ranchat.dao.RegUserDAO.";
	
	public int insertRegUser(RegUserVO userBean) {
		return sqlSession.insert(namespace + "insertRegUser", userBean);
	}
	
	public int selectRegUserCnt(RegUserVO userVO) {
		return sqlSession.selectOne(namespace + "selectRegUserCnt", userVO);
	}
	
	public ArrayList<RegUserVO> selectRegUser(RegUserVO userVO) {
		return (ArrayList)sqlSession.selectList(namespace + "selectRegUser", userVO);
	}
	
}
