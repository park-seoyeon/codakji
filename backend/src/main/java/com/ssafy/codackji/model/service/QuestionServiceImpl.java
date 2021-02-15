package com.ssafy.codackji.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.codackji.model.QuestionDto;
import com.ssafy.codackji.model.mapper.QuestionMapper;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<QuestionDto> allQuestion() throws Exception{
		return sqlSession.getMapper(QuestionMapper.class).allQuestion();
	}
	@Override
	public List<QuestionDto> listQuestion(int user_number) throws Exception {
		return sqlSession.getMapper(QuestionMapper.class).listQuestion(user_number);
	}
	@Override
	public void writeQuestion(QuestionDto questionDto) throws Exception {
		sqlSession.getMapper(QuestionMapper.class).writeQuestion(questionDto);
	}
	@Override
	public QuestionDto getQuestion(int question_number) throws Exception {
		return sqlSession.getMapper(QuestionMapper.class).getQuestion(question_number);
	}
	@Override
	@Transactional
	public boolean editQuestion(QuestionDto questionDto) throws Exception {
		return sqlSession.getMapper(QuestionMapper.class).editQuestion(questionDto) == 1;
	}
	@Override
	@Transactional
	public boolean deleteQuestion(int question_number) throws Exception {
		return sqlSession.getMapper(QuestionMapper.class).deleteQuestion(question_number) == 1;
	}

}
