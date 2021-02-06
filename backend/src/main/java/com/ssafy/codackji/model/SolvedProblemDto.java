package com.ssafy.codackji.model;

public class SolvedProblemDto {

	private int user_number; //문제 푼 사용자 번호
	private int problem_number; //푼 문제의 번호
	private String problem_title; //문제 이름 - 나중에 추가
	private String problem_content; //문제 내용 - 나중에 추가
	private String problem_group; //문제 유형 - 나중에 추가
	private String solved_problem_content; //작성한 코드
	private boolean solved_problem_correct; //정답 여부
	private String language; //사용 언어
	
	public SolvedProblemDto() {}

	public int getUser_number() {
		return user_number;
	}

	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}

	public int getProblem_number() {
		return problem_number;
	}

	public void setProblem_number(int problem_number) {
		this.problem_number = problem_number;
	}

	public String getSolved_problem_content() {
		return solved_problem_content;
	}

	public void setSolved_problem_content(String solved_problem_content) {
		this.solved_problem_content = solved_problem_content;
	}

	public boolean isSolved_problem_correct() {
		return solved_problem_correct;
	}

	public void setSolved_problem_correct(boolean solved_problem_correct) {
		this.solved_problem_correct = solved_problem_correct;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	

	public String getProblem_title() {
		return problem_title;
	}

	public void setProblem_title(String problem_title) {
		this.problem_title = problem_title;
	}

	public String getProblem_content() {
		return problem_content;
	}

	public void setProblem_content(String problem_content) {
		this.problem_content = problem_content;
	}

	public String getProblem_group() {
		return problem_group;
	}

	public void setProblem_group(String problem_group) {
		this.problem_group = problem_group;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SolvedProblemDto [user_number=").append(user_number).append(", problem_number=")
				.append(problem_number).append(", problem_title=").append(problem_title).append(", problem_content=")
				.append(problem_content).append(", problem_group=").append(problem_group)
				.append(", solved_problem_content=").append(solved_problem_content).append(", solved_problem_correct=")
				.append(solved_problem_correct).append(", language=").append(language).append("]");
		return builder.toString();
	}	
	
}
