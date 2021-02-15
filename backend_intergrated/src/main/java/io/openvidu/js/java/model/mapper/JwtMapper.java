package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.MemberDto;


@Mapper
public interface JwtMapper {

	boolean isInTime(String token) throws SQLException;
	void setToken(MemberDto memberDto)throws SQLException;

}
