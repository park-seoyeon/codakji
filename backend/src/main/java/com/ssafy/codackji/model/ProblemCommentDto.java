package com.ssafy.codackji.model;

public class ProblemCommentDto {

	private int comment_number; //댓글 번호(AutoIncremented, PK값)
	private int problem_number; //댓글이 달린 문제 번호
	private int user_number; //댓글을 남긴 사용자 번호
	private String comment_content; //댓글 내용
	private String created_at; //처음 댓글 생성 시각
	private String updated_at; //가장 마지막으로 수정된 시각
	private int likes; //좋아요 수
	private String token;
	
	public ProblemCommentDto(){}


	public int getComment_number() {
		return comment_number;
	}

	public void setComment_number(int comment_number) {
		this.comment_number = comment_number;
	}

	public int getProblem_number() {
		return problem_number;
	}

	public void setProblem_number(int problem_number) {
		this.problem_number = problem_number;
	}

	public int getUser_number() {
		return user_number;
	}

	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "ProblemCommentDto [comment_number=" + comment_number + ", problem_number=" + problem_number
				+ ", user_number=" + user_number + ", comment_content=" + comment_content + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + ", likes=" + likes + ", token=" + token + "]";
	}
	
	
	
	
	
}
