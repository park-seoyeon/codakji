package com.ssafy.codackji.model.service;

import com.ssafy.codackji.model.QuestionDto;

public interface AnswerService {

	boolean writeAnswer(QuestionDto questionDto);

	boolean editAnswer(QuestionDto questionDto);

	boolean deleteAnswer(QuestionDto questionDto);

}
