package com.ssafy.codackji.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.codackji.model.MemberDto;

@Mapper
public interface MemberMapper {

	public void addUser(MemberDto memberDto);

	public int emailCheck(String email);

}
