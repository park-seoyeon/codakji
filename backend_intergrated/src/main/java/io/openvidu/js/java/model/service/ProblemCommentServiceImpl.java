package io.openvidu.js.java.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.model.ProblemCommentDto;
import io.openvidu.js.java.model.mapper.ProblemCommentMapper;


@Service
public class ProblemCommentServiceImpl implements ProblemCommentService{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean writeProblemComment(ProblemCommentDto problemCommentDto) throws Exception {
		return sqlSession.getMapper(ProblemCommentMapper.class).writeProblemComment(problemCommentDto) ==1;
	}
	
	@Override
	public List<ProblemCommentDto> listProblemComment(int problem_number) throws Exception{
		return sqlSession.getMapper(ProblemCommentMapper.class).listProblemComment(problem_number);
	}
	
	@Override
	public boolean updateProblemComment(ProblemCommentDto problemCommentDto) throws Exception{
		return sqlSession.getMapper(ProblemCommentMapper.class).updateProblemComment(problemCommentDto) == 1;
	}
	
	@Override
	public boolean deleteProblemComment(int comment_number) throws Exception{
		return sqlSession.getMapper(ProblemCommentMapper.class).deleteProblemComment(comment_number) == 1;
	}
	
	/*
	 * @Override public int getLikes(int comment_number) throws Exception{ return
	 * sqlSession.getMapper(ProblemCommentMapper.class).getLikes(comment_number); }
	 */
	
	@Override
	public boolean updateLikes(ProblemCommentDto problemCommentDto) throws Exception{
		return sqlSession.getMapper(ProblemCommentMapper.class).updateLikes(problemCommentDto) == 1;
	}
}
