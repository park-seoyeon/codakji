package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.ProblemCommentDto;

@Mapper
public interface ProblemCommentMapper {

	public int writeProblemComment(ProblemCommentDto problemCommentDto) throws SQLException;

	public List<ProblemCommentDto> listProblemComment(int problem_number) throws SQLException;

	public int updateProblemComment(ProblemCommentDto problemCommentDto) throws SQLException;

	public int deleteProblemComment(int comment_number) throws SQLException;

	public int updateLikes(ProblemCommentDto problemCommentDto) throws SQLException;
}
