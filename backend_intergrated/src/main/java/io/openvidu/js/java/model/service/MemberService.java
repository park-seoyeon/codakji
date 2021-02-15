package io.openvidu.js.java.model.service;

import io.openvidu.js.java.model.MemberDto;

public interface MemberService {

	void addUser(MemberDto memberDto);
	int emailCheck(String email);
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String email) throws Exception;
	public boolean updateUser(MemberDto memberDto) throws Exception;
	public boolean deleteUser(String email) throws Exception;
	public boolean updatePassword(MemberDto memberDto) throws Exception;
	public MemberDto socialLogin(MemberDto memberDto) throws Exception;
}
