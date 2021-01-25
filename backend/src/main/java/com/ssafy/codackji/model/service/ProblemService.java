package com.ssafy.codackji.model.service;

import java.util.List;

import com.ssafy.codackji.model.ProblemDto;

public interface ProblemService {

	List<ProblemDto> listProblem(int problem_rank) throws Exception;

	ProblemDto getProblem(int problem_number) throws Exception;

}
