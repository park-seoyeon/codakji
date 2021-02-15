package com.ssafy.codackji.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.codackji.model.QuestionDto;
import com.ssafy.codackji.model.mapper.AnswerMapper;
import com.ssafy.codackji.model.mapper.QuestionMapper;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private SqlSession sqlSession;
	@Override
	public boolean writeAnswer(QuestionDto questionDto) {
		return sqlSession.getMapper(AnswerMapper.class).writeAnswer(questionDto) == 1;
	}
	@Override
	public boolean editAnswer(QuestionDto questionDto) {
		return sqlSession.getMapper(AnswerMapper.class).editAnswer(questionDto) == 1;
	}
	@Override
	public boolean deleteAnswer(QuestionDto questionDto) {
		return sqlSession.getMapper(AnswerMapper.class).deleteAnswer(questionDto) == 1;
	}

}
