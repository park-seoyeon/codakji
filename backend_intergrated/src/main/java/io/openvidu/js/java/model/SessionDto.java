package io.openvidu.js.java.model;

import javax.servlet.http.HttpSession;

import io.openvidu.java.client.Session;

public class SessionDto {
	
	private String name;//유저이름
	private String room_name; //사용자가 지정한 미팅룸 이름
	private int room_number; //미팅룸 번호
	//private String session_name;//중복 없는 난수 발생
	private HttpSession httpSession;
	private Session session;
	private String session_name;
	private String token;//유저 토큰 (세션 토큰 아님)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	
	public HttpSession getHttpSession() {
		return httpSession;
	}
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	public String getSession_name() {
		return session_name;
	}
	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}
	@Override
	public String toString() {
		return "SessionDto [name=" + name + ", room_name=" + room_name + ", room_number=" + room_number
				+ ", httpSession=" + httpSession + ", session=" + session + ", token=" + token + "]";
	}

	
	
}
