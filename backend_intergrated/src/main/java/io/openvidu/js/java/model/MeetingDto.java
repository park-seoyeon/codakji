package io.openvidu.js.java.model;

import io.openvidu.java.client.Session;

public class MeetingDto {
	private int room_number;
	private String room_name;
	private int room_publisher_number;
	private String room_publisher_name;
	private String room_description;
	private int room_people_cnt;
	private Session session;
	private String session_name;
	private int user_number;
	private String user_token;
	private String token;
	private boolean screenShare;
	
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public int getRoom_publisher_number() {
		return room_publisher_number;
	}
	public void setRoom_publisher_number(int room_publisher_number) {
		this.room_publisher_number = room_publisher_number;
	}
	public String getRoom_publisher_name() {
		return room_publisher_name;
	}
	public void setRoom_publisher_name(String room_publisher_name) {
		this.room_publisher_name = room_publisher_name;
	}
	public int getRoom_people_cnt() {
		return room_people_cnt;
	}
	public String getRoom_description() {
		return room_description;
	}
	public void setRoom_description(String room_description) {
		this.room_description = room_description;
	}
	public void setRoom_people_cnt(int room_people_cnt) {
		this.room_people_cnt = room_people_cnt;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public int getUser_number() {
		return user_number;
	}
	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}
	
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public boolean isScreenShare() {
		return screenShare;
	}
	public void setScreenShare(boolean screenShare) {
		this.screenShare = screenShare;
	}
	@Override
	public String toString() {
		return "MeetingDto [room_number=" + room_number + ", room_name=" + room_name + ", room_publisher_number="
				+ room_publisher_number + ", room_publisher_name=" + room_publisher_name + ", room_description="
				+ room_description + ", room_people_cnt=" + room_people_cnt + ", session=" + session + ", session_name="
				+ session_name + ", user_number=" + user_number + ", user_token=" + user_token + ", token=" + token
				+ ", screenShare=" + screenShare + "]";
	}
	
	
}
