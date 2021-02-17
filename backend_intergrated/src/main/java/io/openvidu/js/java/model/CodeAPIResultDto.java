package io.openvidu.js.java.model;

import java.util.List;

public class CodeAPIResultDto {
	private String my_output; //코드 실행 결과 (올바른 실행결과 or 에러메세지 출력) 
	private String correct_output; //올바른 코드 실행결과 //추가
	private String statusCode; //API실행 결과
	private String memory; //메모리 사용량
	private String cpuTime; //cpu 사용 시간
	private String error; //컴파일 에러난 경우 에러 분석
	private boolean answer; //정답여부
	private String my_code; //나의 코드 //추가
	private String correct_code; //올바른 코드 //추가
	private int img_number; // 해설 이미지 개수 //추가
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
