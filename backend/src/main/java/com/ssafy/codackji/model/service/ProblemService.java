package com.ssafy.codackji.model.service;

import java.util.List;

import com.ssafy.codackji.model.ProblemDto;
import com.ssafy.codackji.model.SolvedProblemDto;

public interface ProblemService {

	List<ProblemDto> allProblem() throws Exception;
	
	List<ProblemDto> listProblem(int problem_rank) throws Exception;
	
	List<SolvedProblemDto> userSolvedProblem(int user_number) throws Exception;

	ProblemDto getProblem(int problem_number) throws Exception;

}
