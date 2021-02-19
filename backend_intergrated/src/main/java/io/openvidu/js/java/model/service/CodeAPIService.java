package io.openvidu.js.java.model.service;

import java.sql.SQLException;
import java.util.List;

import io.openvidu.js.java.model.CodeAPIDto;
import io.openvidu.js.java.model.CodeAPIResponseDto;
import io.openvidu.js.java.model.SolvedProblemDto;

public interface CodeAPIService {

	public String getInput1(int problem_number) throws Exception;

	public String getInput2(int problem_number) throws Exception;

	public String getInput3(int problem_number) throws Exception;

	public String getInput4(int problem_number) throws Exception;

	public String getInput5(int problem_number) throws Exception;

	public String getOutput1(int problem_number) throws Exception;

	public String getOutput2(int problem_number) throws Exception;

	public String getOutput3(int problem_number) throws Exception;

	public String getOutput4(int problem_number) throws Exception;

	public String getOutput5(int problem_number) throws Exception;

	public boolean updateSubmit(int problem_number) throws Exception;

	public boolean updateCorrect(int problem_number) throws Exception;

	public void addSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception;

	public String getLanguageVersion(String name) throws Exception;

	public CodeAPIResponseDto codeTest(CodeAPIDto codeAPIDto) throws Exception;


	public String getCorrectJavaCode(int problem_number) throws Exception;
	
	public String getCorrectPythonCode(int problem_number) throws Exception;
	
	public int getImgNumber(int problem_number) throws Exception;
	
	public int getSolvedProblemNumber(CodeAPIDto codeAPIDto) throws Exception;
	
	public boolean updateSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception;
	
	public SolvedProblemDto getSolvedProblemInfo(int solved_problem_number) throws Exception;
	
	public List<String> getAnalysisImage(int problem_number) throws Exception;
}
