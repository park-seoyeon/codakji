package com.ssafy.codackji.model;

public class NoticeDto {
	
	private int notice_number;
	private int admin_user_number;
	private String notice_title;
	private String notice_content;
	private String created_at;
	private String updated_at;
	private int views;
	
	public NoticeDto() {
		super();
	}
	
	public NoticeDto(int admin_user_number, String notice_title, String notice_content) {
		super();
		this.admin_user_number = admin_user_number;
		this.notice_title = notice_title;
		this.notice_content = notice_content;
	}

	public int getNotice_number() {
		return notice_number;
	}
	public void setNotice_number(int notice_number) {
		this.notice_number = notice_number;
	}
	public int getAdmin_user_number() {
		return admin_user_number;
	}
	public void setAdmin_user_number(int admin_user_number) {
		this.admin_user_number = admin_user_number;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NoticeDto [notice_number=").append(notice_number).append(", admin_user_number=")
				.append(admin_user_number).append(", notice_title=").append(notice_title).append(", notice_content=")
				.append(notice_content).append(", created_at=").append(created_at).append(", updated_at=")
				.append(updated_at).append(", views=").append(views).append("]");
		return builder.toString();
	}

	
	
	
}
