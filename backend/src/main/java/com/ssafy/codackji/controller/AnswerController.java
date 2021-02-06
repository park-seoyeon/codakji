package com.ssafy.codackji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.QuestionDto;
import com.ssafy.codackji.model.service.AnswerService;
import com.ssafy.codackji.model.service.JwtService;
import com.ssafy.codackji.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("AnswerController V1")
@RestController
@RequestMapping("/answer")
public class AnswerController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemberService memberService;

	@Autowired
	private AnswerService answerService;


	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "답변 작성_토큰 검사를 한다", notes = "답변을 작성한다", response = String.class)
	@PutMapping("/write")
	public ResponseEntity<String> writeAnswer(
			@RequestBody @ApiParam(value = "작성한 답변Dto", required = true) QuestionDto questionDto) throws Exception {
		String token = questionDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			String email = jwtService.getUserEmail(token);
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(email);
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
			int teacher_number = memberService.userInfo(email).getUser_number();
			questionDto.setTeach_user_number(teacher_number);
			if(answerService.writeAnswer(questionDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}
	@ApiOperation(value = "답변 수정_토큰 검사를 한다", notes = "답변을 수정한다", response = String.class)
	@PutMapping("/edit")
	public ResponseEntity<String> editAnswer(
			@RequestBody @ApiParam(value = "수정한 답변Dto", required = true) QuestionDto questionDto) throws Exception {
		String token = questionDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			String email = jwtService.getUserEmail(token);
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(email);
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
			int teacher_number = memberService.userInfo(email).getUser_number();
			System.out.println("답변작성자:"+questionDto.getTeach_user_number()+"/ 토큰유저:"+teacher_number);
			if(teacher_number!=questionDto.getTeach_user_number()) {//답변 작성자가 본인이 아닐 경우
				return new ResponseEntity<String>(FAIL, HttpStatus.OK);
			}
			if(answerService.editAnswer(questionDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}
	@ApiOperation(value = "답변 삭제_토큰 검사를 한다", notes = "답변을 삭제한다", response = String.class)
	@PutMapping("/delete")
	public ResponseEntity<String> deleteAnswer(
			@RequestBody @ApiParam(value = "삭제할 답변Dto", required = true) QuestionDto questionDto) throws Exception {
		String token = questionDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			String email = jwtService.getUserEmail(token);
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(email);
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
			int teacher_number = memberService.userInfo(email).getUser_number();
			if(teacher_number!=questionDto.getTeach_user_number()) {//답변 작성자가 본인이 아닐 경우
				return new ResponseEntity<String>(FAIL, HttpStatus.OK);
			}
			questionDto.setQuestion_reply(null);
			if(answerService.deleteAnswer(questionDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}
}
