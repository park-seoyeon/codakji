package com.ssafy.codackji.model.service;

import java.util.List;
import com.ssafy.codackji.model.ProblemCommentDto;

public interface ProblemCommentService {
	public boolean writeProblemComment(ProblemCommentDto problemCommentDto) throws Exception;
	public List<ProblemCommentDto> listProblemComment(int problem_number) throws Exception;
	public boolean updateProblemComment(ProblemCommentDto problemCommentDto) throws Exception;
	public boolean deleteProblemComment(int comment_number) throws Exception;
	//public int getLikes(int comment_number) throws Exception;
	public boolean updateLikes(ProblemCommentDto problemCommentDto) throws Exception;
}

