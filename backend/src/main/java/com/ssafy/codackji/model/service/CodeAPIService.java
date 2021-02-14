package com.ssafy.codackji.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.codackji.model.CodeAPIDto;
import com.ssafy.codackji.model.CodeAPIResponseDto;
import com.ssafy.codackji.model.SolvedProblemDto;

public interface CodeAPIService {

	// 테스트 케이스 input 파일 가져오기
	public String getInput1(int problem_number) throws Exception;

	public String getInput2(int problem_number) throws Exception;

	public String getInput3(int problem_number) throws Exception;

	public String getInput4(int problem_number) throws Exception;

	public String getInput5(int problem_number) throws Exception;

	// 테스트 케이스 output 파일 가져오기
	public String getOutput1(int problem_number) throws Exception;

	public String getOutput2(int problem_number) throws Exception;

	public String getOutput3(int problem_number) throws Exception;

	public String getOutput4(int problem_number) throws Exception;

	public String getOutput5(int problem_number) throws Exception;

	// 문제 제출 횟수 증가시키기
	public boolean updateSubmit(int problem_number) throws Exception;

	// 문제 정답 수 증가시키기
	public boolean updateCorrect(int problem_number) throws Exception;

	// 문제 푼 결과 저장하기
	public void addSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception;

	// 언어 버전 가져오기
	public String getLanguageVersion(String name) throws Exception;

	// API 실행
	public CodeAPIResponseDto codeTest(CodeAPIDto codeAPIDto) throws Exception;

	// API 컴파일 에러 분석하기 - 머신러닝
	// 구현 예정

	// Java 정답 Code 가져오기
	public String getCorrectJavaCode(int problem_number) throws Exception;
	
	// Python 정답 Code 가져오기
	public String getCorrectPythonCode(int problem_number) throws Exception;
	
	// 해설 Image 개수 가져오기
	public int getImgNumber(int problem_number) throws Exception;
	
	// solvedProblemNumber를 가져오기
	public int getSolvedProblemNumber(CodeAPIDto codeAPIDto) throws Exception;
	
	// SolvedProblem을 업데이트하기
	public boolean updateSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception;
	
	// solvedProblem 정보 가져오기
	public SolvedProblemDto getSolvedProblemInfo(int solved_problem_number) throws Exception;
	
	public List<String> getAnalysisImage(int problem_number) throws Exception;
}
