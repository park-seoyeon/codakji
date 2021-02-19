package io.openvidu.js.java.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.model.CodeAPIDto;
import io.openvidu.js.java.model.CodeAPIResponseDto;
import io.openvidu.js.java.model.CodeAPIResultDto;
import io.openvidu.js.java.model.MemberDto;
import io.openvidu.js.java.model.SolvedProblemDto;
import io.openvidu.js.java.model.service.CodeAPIService;
import io.openvidu.js.java.model.service.CodeFeedbackService;
import io.openvidu.js.java.model.service.JwtService;
import io.openvidu.js.java.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("CodeAPIController V1")
@RestController
@RequestMapping("/codeAPI")
public class CodeAPIController {

	@Autowired
	private CodeAPIService codeAPIService;
	
	@Autowired
	private CodeFeedbackService codeFeedbackService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "코드 실행하기", notes = "단순히 코드만 미리 실행 - DB반영 X", response = Map.class)
	@PostMapping("/test")
	public ResponseEntity<CodeAPIResponseDto> test(
			@RequestBody @ApiParam(value = "채점할 코드 정보(input은 사용자 입력 기준)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {

		HttpStatus status = null;

		String token = codeAPIDto.getToken();
		String email="";
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			MemberDto memberDto = new MemberDto();
			email = jwtService.getUserEmail(token);
			memberDto.setUser_number(memberService.userInfo(email).getUser_number());		
			memberDto.setEmail(email);
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
		} else {
			CodeAPIResponseDto rtnNull = new CodeAPIResponseDto();
			return new ResponseEntity<CodeAPIResponseDto>(rtnNull, HttpStatus.UNAUTHORIZED);
		}

		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		try {
			codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());

		String output = codeAPIResponseDto.getOutput().trim();
		if(output.equals(codeAPIService.getOutput1(codeAPIDto.getProblem_number()).trim())) {
			codeAPIResponseDto.setAnswer(true);
		}else {
			codeAPIResponseDto.setAnswer(false);
			String error = codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage());
			codeAPIResponseDto.setError(error);
		}

		status = HttpStatus.OK;
		return new ResponseEntity<CodeAPIResponseDto>(codeAPIResponseDto, status);
	}

	@ApiOperation(value = "코드 제출하기", notes = "제출하기 - DB반영됨", response = Map.class)
	@PostMapping("/submit")
	public ResponseEntity<Integer> submit(
			@RequestBody @ApiParam(value = "채점할 코드 정보(DB에 있는 TC기준으로 실행)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {

		HttpStatus status = null;

		String token = codeAPIDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(jwtService.getUserEmail(token));
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
		} else {
			return new ResponseEntity<Integer>(0, HttpStatus.UNAUTHORIZED);
		}

		String email = jwtService.getUserEmail(token);
		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());

		try {
			codeAPIService.updateSubmit(codeAPIDto.getProblem_number());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		SolvedProblemDto solvedProblemDto = new SolvedProblemDto();
		solvedProblemDto.setUser_number(codeAPIDto.getUser_number());
		solvedProblemDto.setProblem_number(codeAPIDto.getProblem_number());
		solvedProblemDto.setSolved_problem_content(codeAPIDto.getScript());
		solvedProblemDto.setSolved_problem_correct(false);
		solvedProblemDto.setLanguage(codeAPIDto.getLanguage());
		
		try {
			codeAPIService.addSolvedProblem(solvedProblemDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int solved_problem_number = codeAPIService.getSolvedProblemNumber(codeAPIDto);
		
		status = HttpStatus.OK;
		return new ResponseEntity<Integer>(solved_problem_number, status);
	}
	
	
	@ApiOperation(value = "제출 결과 확인하기", notes = "결과 확인하기 - DB반영됨", response = Map.class)
	@PostMapping("/result")
	public ResponseEntity<CodeAPIResultDto> result(
		@RequestBody @ApiParam(value = "채점할 코드 정보(DB에 있는 TC기준으로 실행)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {
		
		HttpStatus status = null;

		int solved_problem_number = codeAPIDto.getSolved_problem_number();
		SolvedProblemDto solvedProblemDto = codeAPIService.getSolvedProblemInfo(solved_problem_number);
		boolean api_done = solvedProblemDto.isApi_done();
		
		String token = codeAPIDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(jwtService.getUserEmail(token));
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
		} else {
			CodeAPIResultDto rtnNull = new CodeAPIResultDto();
			return new ResponseEntity<CodeAPIResultDto>(rtnNull, HttpStatus.UNAUTHORIZED);
		}

		String email = jwtService.getUserEmail(token);
		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());
		
		if(memberdto.getUser_number() != codeAPIService.getSolvedProblemInfo(solved_problem_number).getUser_number()) {
			CodeAPIResultDto rtnNull = new CodeAPIResultDto();
			return new ResponseEntity<CodeAPIResultDto>(rtnNull, HttpStatus.FORBIDDEN);
		}
			
		if(api_done) {

			String correct_Java_code = codeAPIService.getCorrectJavaCode(solvedProblemDto.getProblem_number());
			String correct_Python_code = codeAPIService.getCorrectPythonCode(solvedProblemDto.getProblem_number());
			int image_number = codeAPIService.getImgNumber(solvedProblemDto.getProblem_number());
			
			CodeAPIResultDto codeAPIResultDto = new CodeAPIResultDto();
			codeAPIResultDto.setAnswer(solvedProblemDto.isSolved_problem_correct());
			
			if(solvedProblemDto.getLanguage().equals("java"))
				codeAPIResultDto.setCorrect_code(correct_Java_code);
			else if(solvedProblemDto.getLanguage().equals("python3"))
				codeAPIResultDto.setCorrect_code(correct_Python_code);
			else
				codeAPIResultDto.setCorrect_code(null);
			
			codeAPIResultDto.setCorrect_output(solvedProblemDto.getCorrect_output());
			codeAPIResultDto.setError(null);
			codeAPIResultDto.setImg_number(image_number);
			codeAPIResultDto.setMy_code(solvedProblemDto.getSolved_problem_content());
			codeAPIResultDto.setMy_output(solvedProblemDto.getMy_output());
			codeAPIResultDto.setImg_content(codeAPIService.getAnalysisImage(solvedProblemDto.getProblem_number()));
			
			status = HttpStatus.OK;
			return new ResponseEntity<CodeAPIResultDto>(codeAPIResultDto, status);
		}

		codeAPIDto.setScript(solvedProblemDto.getSolved_problem_content());
		codeAPIDto.setLanguage(solvedProblemDto.getLanguage());
		codeAPIDto.setProblem_number(solvedProblemDto.getProblem_number());
		

		String input = "";
		String output = "";
		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		String user_output = "";
		boolean flag = true;

		try {
			input = codeAPIService.getInput1(codeAPIDto.getProblem_number());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		input.trim();

		try {
			output = codeAPIService.getOutput1(codeAPIDto.getProblem_number());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		output.trim();

		codeAPIDto.setUser_input(input);
		codeAPIResponseDto = new CodeAPIResponseDto();
		try {
			codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user_output = codeAPIResponseDto.getOutput();
		user_output = user_output.trim();
		if (output.trim().equals(user_output)) {
			codeAPIResponseDto.setAnswer(true);
		} else {
			codeAPIResponseDto.setAnswer(false);
			codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			flag = false;
		}

		if(flag) {
			try {
				input = codeAPIService.getInput2(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			input.trim();
			
			try {
				output = codeAPIService.getOutput2(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			output.trim();

			codeAPIDto.setUser_input(input);
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true);
			} else {
				flag = false;
				codeAPIResponseDto.setAnswer(false);
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
			
		}

		if(flag) {
			try {
				input = codeAPIService.getInput3(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			input.trim();
			
			try {
				output = codeAPIService.getOutput3(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			output.trim();
			
			codeAPIDto.setUser_input(input);
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true);
			} else {
				flag = false;
				codeAPIResponseDto.setAnswer(false);
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
		}

		if(flag) {
			try {
				input = codeAPIService.getInput4(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			input.trim();
			
			try {
				output = codeAPIService.getOutput4(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			output.trim();
			
			codeAPIDto.setUser_input(input);
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true);
			} else {
				flag = false;
				codeAPIResponseDto.setAnswer(false);
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
		}

		if(flag) {
			try {
				input = codeAPIService.getInput5(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			input.trim();
			
			try {
				output = codeAPIService.getOutput5(codeAPIDto.getProblem_number());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			output.trim();
			
			codeAPIDto.setUser_input(input);
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true);
			} else {
				flag = false;
				codeAPIResponseDto.setAnswer(false);
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}

			if(flag) {
				codeAPIResponseDto.setAnswer(true);
				try {
					codeAPIService.updateCorrect(codeAPIDto.getProblem_number());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		solvedProblemDto.setSolved_problem_correct(codeAPIResponseDto.isAnswer());
		solvedProblemDto.setApi_done(true);

		solvedProblemDto.setMy_output(user_output);
		
		solvedProblemDto.setCorrect_output(output);
		
		
		String correct_Java_code = codeAPIService.getCorrectJavaCode(codeAPIDto.getProblem_number());
		String correct_Python_code = codeAPIService.getCorrectPythonCode(codeAPIDto.getProblem_number());
		int image_number = codeAPIService.getImgNumber(codeAPIDto.getProblem_number());
		
		CodeAPIResultDto codeAPIResultDto = new CodeAPIResultDto();
		codeAPIResultDto.setAnswer(codeAPIResponseDto.isAnswer());
		
		if(solvedProblemDto.getLanguage().equals("java"))
			codeAPIResultDto.setCorrect_code(correct_Java_code);
		else if(solvedProblemDto.getLanguage().equals("python3"))
			codeAPIResultDto.setCorrect_code(correct_Python_code);
		else
			codeAPIResultDto.setCorrect_code(null);
		
		codeAPIResultDto.setCorrect_output(output);
		codeAPIResultDto.setCpuTime(codeAPIResponseDto.getCpuTime());
		codeAPIResultDto.setError(codeAPIResponseDto.getError());
		codeAPIResultDto.setImg_number(image_number);
		codeAPIResultDto.setMemory(codeAPIResponseDto.getMemory());
		codeAPIResultDto.setMy_code(codeAPIDto.getScript());
		
		if (codeAPIResponseDto.isAnswer() == false && !codeAPIResponseDto.getError().equals("simpleerror"))
			codeAPIResultDto.setMy_output("문법적인 오류가 발생했어. 자세한 이유를 알고싶으면 힌트보기를 눌러봐!");
		else
			codeAPIResultDto.setMy_output(user_output);
		codeAPIResultDto.setStatusCode(codeAPIResponseDto.getStatusCode());
		codeAPIResultDto.setImg_content(codeAPIService.getAnalysisImage(solvedProblemDto.getProblem_number()));

		try {
			codeAPIService.updateSolvedProblem(solvedProblemDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		status = HttpStatus.OK;
		return new ResponseEntity<CodeAPIResultDto>(codeAPIResultDto, status);
	}
}