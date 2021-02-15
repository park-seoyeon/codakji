package io.openvidu.js.java.model;

public class QuestionDto {
	
	private int question_number;
	private int user_number;
	private int teach_user_number;
	private int problem_number;
	private String problem_title;
	private String question_title;
	private String question_content;
	private boolean question_answered;
	private boolean question_check;
	private String created_at;
	private String updated_at;
	private String question_reply;
	private String token;
	
	
	public int getQuestion_number() {
		return question_number;
	}
	public void setQuestion_number(int question_number) {
		this.question_number = question_number;
	}
	public int getUser_number() {
		return user_number;
	}
	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}
	public int getTeach_user_number() {
		return teach_user_number;
	}
	public void setTeach_user_number(int teach_user_number) {
		this.teach_user_number = teach_user_number;
	}
	
	public int getProblem_number() {
		return problem_number;
	}
	public void setProblem_number(int problem_number) {
		this.problem_number = problem_number;
	}
	public String getQuestion_title() {
		return question_title;
	}
	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public boolean isQuestion_answered() {
		return question_answered;
	}
	public void setQuestion_answered(boolean question_answered) {
		this.question_answered = question_answered;
	}
	public boolean isQuestion_check() {
		return question_check;
	}
	public void setQuestion_check(boolean question_check) {
		this.question_check = question_check;
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
	
	public String getQuestion_reply() {
		return question_reply;
	}
	public void setQuestion_reply(String question_reply) {
		this.question_reply = question_reply;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getProblem_title() {
		return problem_title;
	}
	public void setProblem_title(String problem_title) {
		this.problem_title = problem_title;
	}
	@Override
	public String toString() {
		return "QuestionDto [question_number=" + question_number + ", user_number=" + user_number
				+ ", teach_user_number=" + teach_user_number + ", problem_number=" + problem_number + ", problem_title="
				+ problem_title + ", question_title=" + question_title + ", question_content=" + question_content
				+ ", question_answered=" + question_answered + ", question_check=" + question_check + ", created_at="
				+ created_at + ", updated_at=" + updated_at + ", question_reply=" + question_reply + ", token=" + token
				+ "]";
	}
	
	
	
}
