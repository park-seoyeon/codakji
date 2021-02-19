package io.openvidu.js.java.model;

public class ProblemCommentDto {

	private int comment_number;
	private int problem_number;
	private int user_number;
	private String name;
	private String comment_content;
	private String created_at;
	private String updated_at;
	private int likes;
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


	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProblemCommentDto [comment_number=").append(comment_number).append(", problem_number=")
				.append(problem_number).append(", user_number=").append(user_number).append(", name=").append(name)
				.append(", comment_content=").append(comment_content).append(", created_at=").append(created_at)
				.append(", updated_at=").append(updated_at).append(", likes=").append(likes).append(", token=")
				.append(token).append("]");
		return builder.toString();
	}


}
