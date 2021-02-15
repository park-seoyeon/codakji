package io.openvidu.js.java.model;

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
	private int problem_rank;
	
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


	public int getProblem_rank() {
		return problem_rank;
	}


	public void setProblem_rank(int problem_rank) {
		this.problem_rank = problem_rank;
	}


	@Override
	public String toString() {
		return "SolvedProblemDto [solved_problem_number=" + solved_problem_number + ", user_number=" + user_number
				+ ", problem_number=" + problem_number + ", problem_title=" + problem_title + ", problem_content="
				+ problem_content + ", problem_group=" + problem_group + ", solved_problem_content="
				+ solved_problem_content + ", solved_problem_correct=" + solved_problem_correct + ", language="
				+ language + ", api_done=" + api_done + ", my_output=" + my_output + ", correct_output="
				+ correct_output + ", submit_at=" + submit_at + ", problem_rank=" + problem_rank + "]";
	}


	
}
