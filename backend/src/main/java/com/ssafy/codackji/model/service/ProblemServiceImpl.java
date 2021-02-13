package com.ssafy.codackji.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.codackji.model.ProblemDto;
import com.ssafy.codackji.model.ProblemStatDto;
import com.ssafy.codackji.model.SolvedProblemDto;
import com.ssafy.codackji.model.mapper.ProblemMapper;

@Service
public class ProblemServiceImpl implements ProblemService{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ProblemDto> allProblem() throws Exception {
		return sqlSession.getMapper(ProblemMapper.class).allProblem();
	}
	
	@Override
	public List<ProblemDto> listProblem(int problem_rank) throws Exception {
		return sqlSession.getMapper(ProblemMapper.class).listProblem(problem_rank);
	}
	
	@Override
	public List<SolvedProblemDto> userSolvedProblem(int user_number) throws Exception{
		return sqlSession.getMapper(ProblemMapper.class).userSolvedProblem(user_number);
	}

	@Override
	public ProblemDto getProblem(int problem_number) throws Exception {
		return sqlSession.getMapper(ProblemMapper.class).getProblem(problem_number);
	}

	@Override
	public int getTotal(int rank) throws Exception {
		return sqlSession.getMapper(ProblemMapper.class).getTotal(rank);
	}
}
