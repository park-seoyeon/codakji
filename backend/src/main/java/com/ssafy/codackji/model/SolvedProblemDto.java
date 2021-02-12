package com.ssafy.codackji.model;

public class SolvedProblemDto {

	private int solved_problem_number;
	private int user_number; //문제 푼 사용자 번호
	private int problem_number; //푼 문제의 번호
	private String problem_title; //문제 이름 - 나중에 추가
	private String problem_content; //문제 내용 - 나중에 추가
	private String problem_group; //문제 유형 - 나중에 추가
	private String solved_problem_content; //작성한 코드
	private boolean solved_problem_correct; //정답 여부
	private String language; //사용 언어
	private boolean api_done;
	private String my_output;
	private String correct_output;
	private String submit_at; //사용자가 문제를 제출한 시각
	
	public SolvedProblemDto() {}

	
	public int getSolved_problem_number() {
		return solved_problem_number;
	}


	public void setSolved_problem_number(int solved_problem_number) {
		this.solved_problem_number = solved_problem_number;
	}


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

	
	public boolean isApi_done() {
		return api_done;
	}

	public void setApi_done(boolean api_done) {
		this.api_done = api_done;
	}

	public String getMy_output() {
		return my_output;
	}

	public void setMy_output(String my_output) {
		this.my_output = my_output;
	}

	public String getCorrect_output() {
		return correct_output;
	}


	public void setCorrect_output(String correct_output) {
		this.correct_output = correct_output;
	}


	public String getSubmit_at() {
		return submit_at;
	}


	public void setSubmit_at(String submit_at) {
		this.submit_at = submit_at;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SolvedProblemDto [solved_problem_number=").append(solved_problem_number)
				.append(", user_number=").append(user_number).append(", problem_number=").append(problem_number)
				.append(", problem_title=").append(problem_title).append(", problem_content=").append(problem_content)
				.append(", problem_group=").append(problem_group).append(", solved_problem_content=")
				.append(solved_problem_content).append(", solved_problem_correct=").append(solved_problem_correct)
				.append(", language=").append(language).append(", api_done=").append(api_done).append(", my_output=")
				.append(my_output).append(", correct_output=").append(correct_output).append(", submit_at=")
				.append(submit_at).append("]");
		return builder.toString();
	}


	
	
}
