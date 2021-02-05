package com.ssafy.codackji.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.codackji.model.ProblemDto;

@Mapper
public interface ProblemMapper {

	List<ProblemDto> allProblem() throws SQLException;
	List<ProblemDto> listProblem(int problem_rank) throws SQLException;
	ProblemDto getProblem(int problem_number) throws SQLException;


}
