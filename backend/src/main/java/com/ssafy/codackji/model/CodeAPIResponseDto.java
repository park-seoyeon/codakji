package com.ssafy.codackji.model;

public class CodeAPIResponseDto {

	private String output;
	private String statusCode;
	private String memory;
	private String cpuTime;
	private String error;
	private boolean answer;
	
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
