package com.ssafy.codackji.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addUser(MemberDto memberDto) {
		System.out.println("[service-회원가입]");
		sqlSession.getMapper(MemberMapper.class).addUser(memberDto);
	}

	@Override
	public int emailCheck(String email) {
		return sqlSession.getMapper(MemberMapper.class).emailCheck(email);
	}

}
