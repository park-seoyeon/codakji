package com.ssafy.codackji.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.codackji.model.SolvedProblemDto;

@Mapper
public interface CodeAPIMapper {

	public String getInput1(int problem_number) throws SQLException;

	public String getInput2(int problem_number) throws SQLException;

	public String getInput3(int problem_number) throws SQLException;

	public String getInput4(int problem_number) throws SQLException;

	public String getInput5(int problem_number) throws SQLException;

	public String getOutput1(int problem_number) throws SQLException;

	public String getOutput2(int problem_number) throws SQLException;

	public String getOutput3(int problem_number) throws SQLException;

	public String getOutput4(int problem_number) throws SQLException;

	public String getOutput5(int problem_number) throws SQLException;

	public int updateSubmit(int problem_number) throws SQLException;

	public int updateCorrect(int problem_number) throws SQLException;

	public void addSolvedProblem(SolvedProblemDto solvedProblemDto) throws SQLException;

	public String getLanguageVersion(String name) throws SQLException;
}
