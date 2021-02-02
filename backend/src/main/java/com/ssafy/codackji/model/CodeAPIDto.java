package com.ssafy.codackji.model;

public class CodeAPIDto {
	
	private int problem_number; //문제번호
	private int user_number; //사용자 번호
	private String user_input; //사용자 입력
	private String language; //코드 작성한 언어
	private String script; //코드
	private String user_token; //토큰
	
	
	public CodeAPIDto() {}


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
	

	public String getUser_input() {
		return user_input;
	}


	public void setUser_input(String user_input) {
		this.user_input = user_input;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getScript() {
		return script;
	}


	public void setScript(String script) {
		this.script = script;
	}


	public String getUser_token() {
		return user_token;
	}


	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeAPIDto [problem_number=").append(problem_number).append(", user_number=")
				.append(user_number).append(", language=").append(language).append(", script=").append(script)
				.append(", user_token=").append(user_token).append("]");
		return builder.toString();
	}
	
	
	
}
