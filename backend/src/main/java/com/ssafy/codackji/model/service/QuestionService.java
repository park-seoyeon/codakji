package com.ssafy.codackji.model.service;

import java.util.List;

import com.ssafy.codackji.model.QuestionDto;

public interface QuestionService {

	List<QuestionDto> allQuestion() throws Exception;
	List<QuestionDto> listQuestion(int user_number) throws Exception;
	void writeQuestion(QuestionDto questionDto)throws Exception;
	QuestionDto getQuestion(int question_number) throws Exception;
	boolean editQuestion(QuestionDto questionDto)throws Exception;
	boolean deleteQuestion(int question_number)throws Exception;

}
