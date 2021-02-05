package com.ssafy.codackji.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.codackji.model.MemberDto;

@Mapper
public interface JwtMapper {

	boolean isInTime(String token) throws SQLException;
	void setToken(MemberDto memberDto)throws SQLException;

}
