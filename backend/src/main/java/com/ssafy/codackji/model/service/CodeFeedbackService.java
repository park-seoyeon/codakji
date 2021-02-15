package com.ssafy.codackji.model.service;

import com.ssafy.codackji.model.CodeAPIResponseDto;

public interface CodeFeedbackService {

	String findError(CodeAPIResponseDto codeAPIResponseDto, String language);

}
