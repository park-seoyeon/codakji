package io.openvidu.js.java.model;

public class CodeAPIDto {
	
	private int solved_problem_number;
	private int problem_number; //문제번호
	private int user_number; //사용자 번호
	private String user_input; //사용자 입력
	private String language; //코드 작성한 언어
	private String script; //코드
	private String token; //토큰
	
	
	public CodeAPIDto() {}

	

	public int getSolved_problem_number() {
		return solved_problem_number;
	}



	public void setSolved_problem_number(int solved_problem_number) {
		this.solved_problem_number = solved_problem_number;
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

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}



	@Override
	public String toString() {
		return "CodeAPIDto [solved_problem_number=" + solved_problem_number + ", problem_number=" + problem_number
				+ ", user_number=" + user_number + ", user_input=" + user_input + ", language=" + language + ", script="
				+ script + ", token=" + token + "]";
	}


}
