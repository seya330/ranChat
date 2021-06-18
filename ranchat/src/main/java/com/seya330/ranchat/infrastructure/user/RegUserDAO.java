package com.seya330.ranchat.infrastructure.user;

import java.util.ArrayList;

import com.seya330.ranchat.core.user.vo.RegUserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
