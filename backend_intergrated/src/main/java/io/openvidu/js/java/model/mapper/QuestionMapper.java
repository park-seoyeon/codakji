package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.QuestionDto;


@Mapper
public interface QuestionMapper {

	public List<QuestionDto> allQuestion() throws SQLException;
	public List<QuestionDto> listQuestion(int user_number) throws SQLException;
	public void writeQuestion(QuestionDto questionDto) throws SQLException;
	public QuestionDto getQuestion(int question_number) throws SQLException;
	public int editQuestion(QuestionDto questionDto) throws SQLException;
	public int deleteQuestion(int question_number) throws SQLException;
}