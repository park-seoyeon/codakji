package io.openvidu.js.java.model.service;

import java.util.List;

import io.openvidu.js.java.model.ProblemDto;
import io.openvidu.js.java.model.SolvedProblemDto;

public interface ProblemService {

	List<ProblemDto> allProblem() throws Exception;
	
	List<ProblemDto> listProblem(int problem_rank) throws Exception;
	
	List<SolvedProblemDto> userSolvedProblem(int user_number) throws Exception;

	ProblemDto getProblem(int problem_number) throws Exception;
	
	int getTotal(int rank) throws Exception;
}
