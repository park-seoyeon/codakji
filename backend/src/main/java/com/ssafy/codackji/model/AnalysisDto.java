package com.ssafy.codackji.model;

public class AnalysisDto {

	private int analysis_number;
	private int problem_number;
	private int image_number;
	private String image_content;
	
	public int getAnalysis_number() {
		return analysis_number;
	}
	
	public void setAnalysis_number(int analysis_number) {
		this.analysis_number = analysis_number;
	}
	
	public int getProblem_number() {
		return problem_number;
	}
	
	public void setProblem_number(int problem_number) {
		this.problem_number = problem_number;
	}
	
	public int getImage_number() {
		return image_number;
	}
	
	public void setImage_number(int image_number) {
		this.image_number = image_number;
	}
	
	public String getImage_content() {
		return image_content;
	}
	
	public void setImage_content(String image_content) {
		this.image_content = image_content;
	}

	@Override
	public String toString() {
		return "AnalysisDto [analysis_number=" + analysis_number + ", problem_number=" + problem_number
				+ ", image_number=" + image_number + ", image_content=" + image_content + "]";
	}

}
