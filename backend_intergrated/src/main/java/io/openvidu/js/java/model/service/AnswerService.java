package io.openvidu.js.java.model.service;

import io.openvidu.js.java.model.QuestionDto;

public interface AnswerService {

	boolean writeAnswer(QuestionDto questionDto);

	boolean editAnswer(QuestionDto questionDto);

	boolean deleteAnswer(QuestionDto questionDto);

}
