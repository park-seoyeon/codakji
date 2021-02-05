package com.ssafy.codackji.model.service;

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

}
