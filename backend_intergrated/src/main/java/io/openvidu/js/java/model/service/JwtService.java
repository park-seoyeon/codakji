package io.openvidu.js.java.model.service;

import java.util.Map;

import io.openvidu.js.java.model.MemberDto;


public interface JwtService {

	<T> String create(String key, T data, String subject);
	Map<String, Object> get(String key);
	String getUserId();
	boolean isUsable(String jwt);
	String getUserEmail(String jwt);
	boolean isInTime(String token) throws Exception;
	void setToken(MemberDto memberDto) throws Exception;
	
}
