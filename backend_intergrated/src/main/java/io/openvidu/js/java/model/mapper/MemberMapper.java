package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;
import io.openvidu.js.java.model.MemberDto;

@Mapper
public interface MemberMapper {

	public void addUser(MemberDto memberDto);

	public int emailCheck(String email);
	
	public MemberDto login(MemberDto memberDto) throws SQLException;
	
	public MemberDto userInfo(String email) throws SQLException;
	
	public int updateUser(MemberDto memberDto) throws SQLException;
	
	public int deleteUser(String email) throws SQLException;
	
	public int updatePassword(MemberDto memberDto) throws SQLException;

	public MemberDto socialLogin(MemberDto memberDto) throws SQLException;

}
