package com.ssafy.codackji.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.codackji.model.QuestionDto;

@Mapper
public interface AnswerMapper {

	int writeAnswer(QuestionDto questionDto);

	int editAnswer(QuestionDto questionDto);

	int deleteAnswer(QuestionDto questionDto);

}
