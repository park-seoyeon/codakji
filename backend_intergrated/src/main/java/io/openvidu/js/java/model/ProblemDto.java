package io.openvidu.js.java.model;

public class ProblemDto {

	private int problem_number;
	private int admin_user_number;
	private String problem_title;
	private String problem_content;
	private String problem_group;
	private int problem_rank;
	private String problem_date;// 문제 출제 연도
	private String created_at;
	private String updated_at;
	private int problem_submit;
	private int problem_correct;
	private String problem_output;
	private String problem_input;

	public int getProblem_number() {
		return problem_number;
	}

	public void setProblem_number(int problem_number) {
		this.problem_number = problem_number;
	}

	public int getAdmin_user_number() {
		return admin_user_number;
	}

	public void setAdmin_user_number(int admin_user_number) {
		this.admin_user_number = admin_user_number;
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

	public int getProblem_rank() {
		return problem_rank;
	}

	public void setProblem_rank(int problem_rank) {
		this.problem_rank = problem_rank;
	}

	public String getProblem_date() {
		return problem_date;
	}

	public void setProblem_date(String problem_date) {
		this.problem_date = problem_date;
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

	public int getProblem_submit() {
		return problem_submit;
	}

	public void setProblem_submit(int problem_submit) {
		this.problem_submit = problem_submit;
	}

	public int getProblem_correct() {
		return problem_correct;
	}

	public void setProblem_correct(int problem_correct) {
		this.problem_correct = problem_correct;
	}

	public String getProblem_output() {
		return problem_output;
	}

	public void setProblem_output(String problem_output) {
		this.problem_output = problem_output;
	}

	public String getProblem_input() {
		return problem_input;
	}

	public void setProblem_input(String problem_input) {
		this.problem_input = problem_input;
	}

	@Override
	public String toString() {
		return "ProblemDto [problem_number=" + problem_number + ", admin_user_number=" + admin_user_number
				+ ", problem_title=" + problem_title + ", problem_content=" + problem_content + ", problem_group="
				+ problem_group + ", problem_rank=" + problem_rank + ", problem_date=" + problem_date + ", created_at="
				+ created_at + ", updated_at=" + updated_at + ", problem_submit=" + problem_submit
				+ ", problem_correct=" + problem_correct + ", problem_output=" + problem_output + ", problem_input="
				+ problem_input + "]";
	}

}
