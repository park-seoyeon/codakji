package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.ProblemDto;
import io.openvidu.js.java.model.SolvedProblemDto;


@Mapper
public interface ProblemMapper {

	List<ProblemDto> allProblem() throws SQLException;
	List<ProblemDto> listProblem(int problem_rank) throws SQLException;
	List<SolvedProblemDto> userSolvedProblem(int user_number) throws SQLException;
	ProblemDto getProblem(int problem_number) throws SQLException;
	int getTotal(int rank)throws SQLException;

}
