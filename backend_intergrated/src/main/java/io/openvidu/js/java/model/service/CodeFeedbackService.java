package io.openvidu.js.java.model.service;

import io.openvidu.js.java.model.CodeAPIResponseDto;

public interface CodeFeedbackService {

	String findError(CodeAPIResponseDto codeAPIResponseDto, String language);

}
