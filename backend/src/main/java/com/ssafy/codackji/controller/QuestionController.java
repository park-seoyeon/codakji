package com.ssafy.codackji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.QuestionDto;
import com.ssafy.codackji.model.service.JwtService;
import com.ssafy.codackji.model.service.MemberService;
import com.ssafy.codackji.model.service.QuestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("QuestionController V1")
@RestController
@RequestMapping("/question")
public class QuestionController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "나의 질문 리스트_토큰 검사를 한다", notes="나의 모든 질문 리스트를 반환한다", response = List.class)
	@PostMapping
	public ResponseEntity<List<QuestionDto>> listQuestion(@RequestBody @ApiParam(value="토큰", required=true)String token) throws Exception{
		List<QuestionDto> questionList = null;
		if(jwtService.isUsable(token)) {
			if(jwtService.isInTime(token)) {
				String email = jwtService.getUserEmail(token);
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				System.out.println("토큰 리뉴얼타임 갱신");
				int user_number = memberService.userInfo(email).getUser_number();
				questionList = questionService.listQuestion(user_number);
				return new ResponseEntity<List<QuestionDto>>(questionList, HttpStatus.OK);
				
			}
		}
		return new ResponseEntity<List<QuestionDto>>(questionList, HttpStatus.UNAUTHORIZED);
	}
	
	@ApiOperation(value = "질문 작성_토큰 검사를 한다", notes="질문을 작성한다", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> writeQuestion(@RequestBody @ApiParam(value="작성한 질문Dto", required=true)QuestionDto questionDto) throws Exception{
		String token = questionDto.getToken();
		if(jwtService.isUsable(token)) {
			if(jwtService.isInTime(token)) {
				String email = jwtService.getUserEmail(token);
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				int user_number = memberService.userInfo(email).getUser_number();
				questionDto.setUser_number(user_number);
				questionService.writeQuestion(questionDto);
				return new ResponseEntity<String> (SUCCESS,HttpStatus.OK);
				
			}
		}
		return new ResponseEntity<String> (FAIL,HttpStatus.UNAUTHORIZED);
	}
	
	@ApiOperation(value = "질문 상세 보기_토큰 검사를 한다", notes="질문의 상세내용을 반환한다", response = QuestionDto.class)
	@PostMapping("/view")
	public ResponseEntity<QuestionDto> getQuestion(@RequestBody @ApiParam(value="질문 번호와 토큰이 들어간 질문Dto", required=true) QuestionDto questionDto) throws Exception{
		QuestionDto resultQuestionDto = null;
		String token = questionDto.getToken();
		if(jwtService.isUsable(token)) {
			if(jwtService.isInTime(token)) {
				String email = jwtService.getUserEmail(token);
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				resultQuestionDto = questionService.getQuestion(questionDto.getQuestion_number());
				return new ResponseEntity<QuestionDto> (resultQuestionDto,HttpStatus.OK);
			}
		}
		return new ResponseEntity<QuestionDto> (resultQuestionDto,HttpStatus.UNAUTHORIZED); //리턴 null
	}
	
	@ApiOperation(value = "질문 수정_토큰 검사를 한다", notes="질문을 수정한다", response = String.class)
	@PutMapping
	public ResponseEntity<String> editQuestion(@RequestBody @ApiParam(value="질문 수정 내용과 토큰이 들어간 질문Dto", required=true) QuestionDto questionDto) throws Exception{
		String token = questionDto.getToken();
		if(jwtService.isUsable(token)) {
			if(jwtService.isInTime(token)) {
				String email = jwtService.getUserEmail(token);
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				if(questionService.editQuestion(questionDto)) {
					return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String> (FAIL, HttpStatus.UNAUTHORIZED);
	}
	
	@ApiOperation(value = "질문 삭제_토큰 검사를 한다", notes="질문을 삭제한다", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteQuestion(@RequestBody @ApiParam(value="삭제할 질문 번호와 토큰이 들어간 질문Dto", required=true) QuestionDto questionDto) throws Exception{
		String token = questionDto.getToken();
		if(jwtService.isUsable(token)) {
			if(jwtService.isInTime(token)) {
				String email = jwtService.getUserEmail(token);
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				int question_number = questionDto.getQuestion_number();
				if(questionService.deleteQuestion(question_number)) {
					return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String> (FAIL, HttpStatus.OK);
	}
	
	
}
