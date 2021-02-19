package io.openvidu.js.java.model;

import java.util.List;

public class CodeAPIResultDto {
	private String my_output;
	private String correct_output;
	private String statusCode;
	private String memory;
	private String cpuTime;
	private String error;
	private boolean answer;
	private String my_code;
	private String correct_code;
	private int img_number;
	private List<String> img_content;
	
	public CodeAPIResultDto() {}

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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(String cpuTime) {
		this.cpuTime = cpuTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public String getMy_code() {
		return my_code;
	}

	public void setMy_code(String my_code) {
		this.my_code = my_code;
	}

	public String getCorrect_code() {
		return correct_code;
	}

	public void setCorrect_code(String correct_code) {
		this.correct_code = correct_code;
	}

	public int getImg_number() {
		return img_number;
	}

	public void setImg_number(int img_number) {
		this.img_number = img_number;
	}

	public List<String> getImg_content() {
		return img_content;
	}

	public void setImg_content(List<String> img_content) {
		this.img_content = img_content;
	}

	@Override
	public String toString() {
		return "CodeAPIResultDto [my_output=" + my_output + ", correct_output=" + correct_output + ", statusCode="
				+ statusCode + ", memory=" + memory + ", cpuTime=" + cpuTime + ", error=" + error + ", answer=" + answer
				+ ", my_code=" + my_code + ", correct_code=" + correct_code + ", img_number=" + img_number
				+ ", img_content=" + img_content + "]";
	}
}
