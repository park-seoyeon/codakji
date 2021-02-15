package com.ssafy.codackji.model.service;

import java.sql.SQLException;

import com.ssafy.codackji.model.MemberDto;

public interface MemberService {

	void addUser(MemberDto memberDto);
	int emailCheck(String email);
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String email) throws Exception;
	public boolean updateUser(MemberDto memberDto) throws Exception;
	public boolean deleteUser(String email) throws Exception;
	public boolean updatePassword(MemberDto memberDto) throws Exception;
	public MemberDto socialLogin(MemberDto memberDto) throws Exception;
	public boolean updateProfile(MemberDto memberDto) throws Exception;
	public boolean updateIsProfile(MemberDto memberDto) throws Exception;
}
