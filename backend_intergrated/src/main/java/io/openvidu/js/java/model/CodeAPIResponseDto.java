package io.openvidu.js.java.model;

public class CodeAPIResponseDto {

	private String output; //코드 실행 결과 (올바른 실행결과 or 에러메세지 출력) 
	private String statusCode; //API실행 결과
	private String memory; //메모리 사용량
	private String cpuTime; //cpu 사용 시간
	private String error; //컴파일 에러난 경우 에러 분석
	private boolean answer; //정답여부
	
	public CodeAPIResponseDto() {}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeAPIResponseDto [output=").append(output).append(", statusCode=").append(statusCode)
				.append(", memory=").append(memory).append(", cpuTime=").append(cpuTime).append(", error=")
				.append(error).append("]");
		return builder.toString();
	};
	
	
}
