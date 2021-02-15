package io.openvidu.js.java.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.QuestionDto;


@Mapper
public interface AnswerMapper {

	int writeAnswer(QuestionDto questionDto);

	int editAnswer(QuestionDto questionDto);

	int deleteAnswer(QuestionDto questionDto);

}
