package com.ssafy.codackji.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.CodeAPIDto;
import com.ssafy.codackji.model.CodeAPIResponseDto;
import com.ssafy.codackji.model.CodeAPIResultDto;
import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.SolvedProblemDto;
import com.ssafy.codackji.model.service.CodeAPIService;
import com.ssafy.codackji.model.service.CodeFeedbackService;
import com.ssafy.codackji.model.service.JwtService;
import com.ssafy.codackji.model.service.MemberService;

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

	// 단순히 코드만 미리 실행 - DB반영 X
	@ApiOperation(value = "코드 실행하기", notes = "단순히 코드만 미리 실행 - DB반영 X", response = Map.class)
	@PostMapping("/test")
	public ResponseEntity<CodeAPIResponseDto> test(
			@RequestBody @ApiParam(value = "채점할 코드 정보(input은 사용자 입력 기준)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {

		HttpStatus status = null;

		// 1.토큰검사
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

		// 2.API결과 가져오기
		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		try {
			codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 선택언어, 사용자코드, 사용자input 에 따른 코드 실행결과
		} catch (Exception e) {
			e.printStackTrace();
		}

		// user_number 값 지정하기
		// 토큰에서 이메일 > 이메일에서 userInfo Dto > 에서 user_number가져오기
		//String email = jwtService.getUserEmail(token);
		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());

		System.out.println("[정답]"+codeAPIService.getOutput1(codeAPIDto.getProblem_number()).trim());
		// 3.컴파일 에러가 났다면 채점결과분석 -(에러가 난건지 원하던 결과값인지 판별하는 로직도 필요)
		String output = codeAPIResponseDto.getOutput().trim();
		System.out.println(codeAPIResponseDto.toString());
		System.out.println("[정답 체크]");
		if(output.equals(codeAPIService.getOutput1(codeAPIDto.getProblem_number()).trim())) {
			System.out.println("정답");
			codeAPIResponseDto.setAnswer(true);
		}else {
			System.out.println("오답");
			codeAPIResponseDto.setAnswer(false);
			String error = codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage());
			System.out.println(error);
			codeAPIResponseDto.setError(error);
		}

		// 4.사용자에게 결과값 리턴(아직까지는 실행결과만 그대로 리턴 _ 컴파일 에러가 났으면 에러난 그대로 리턴)
		status = HttpStatus.OK;
		return new ResponseEntity<CodeAPIResponseDto>(codeAPIResponseDto, status);
	}

	// 제출하기 - DB반영
	@ApiOperation(value = "코드 제출하기", notes = "제출하기 - DB반영됨", response = Map.class)
	@PostMapping("/submit")
	public ResponseEntity<Integer> submit(
			@RequestBody @ApiParam(value = "채점할 코드 정보(DB에 있는 TC기준으로 실행)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {

		HttpStatus status = null;

		System.out.println(codeAPIDto);
		System.out.println(codeAPIDto.getToken());
		
		// 1.토큰검사
		String token = codeAPIDto.getToken();
		if (jwtService.isUsable(token) && jwtService.isInTime(token)) {
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(jwtService.getUserEmail(token));
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
		} else {
			return new ResponseEntity<Integer>(0, HttpStatus.UNAUTHORIZED);
		}

		// user_number 값 지정하기
		// 토큰에서 이메일 > 이메일에서 userInfo Dto > 에서 user_number가져오기
		String email = jwtService.getUserEmail(token);
		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());
		

		// 문제 제출 수 증가
		try {
			codeAPIService.updateSubmit(codeAPIDto.getProblem_number());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
		// 6.문제푼 결과 DB에 저장
		SolvedProblemDto solvedProblemDto = new SolvedProblemDto();
		solvedProblemDto.setUser_number(codeAPIDto.getUser_number()); // 문제 푼 사용자 번호
		solvedProblemDto.setProblem_number(codeAPIDto.getProblem_number());// 푼 문제의 번호
		solvedProblemDto.setSolved_problem_content(codeAPIDto.getScript());// 작성한 코드
		solvedProblemDto.setSolved_problem_correct(false); // 정답 여부
		solvedProblemDto.setLanguage(codeAPIDto.getLanguage()); // 사용 언어
		
		try {
			codeAPIService.addSolvedProblem(solvedProblemDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int solved_problem_number = codeAPIService.getSolvedProblemNumber(codeAPIDto);
		
		// 7.사용자에게 푼문제 번호 리턴
		status = HttpStatus.OK;
		return new ResponseEntity<Integer>(solved_problem_number, status);
	}
	
	
	// 결과 확인하기 - DB반영
	@ApiOperation(value = "제출 결과 확인하기", notes = "결과 확인하기 - DB반영됨", response = Map.class)
	@PostMapping("/result")
	public ResponseEntity<CodeAPIResultDto> result(
		@RequestBody @ApiParam(value = "채점할 코드 정보(DB에 있는 TC기준으로 실행)", required = true) CodeAPIDto codeAPIDto)
			throws Exception {
		
		HttpStatus status = null;

		int solved_problem_number = codeAPIDto.getSolved_problem_number();
		SolvedProblemDto solvedProblemDto = codeAPIService.getSolvedProblemInfo(solved_problem_number);
		boolean api_done = solvedProblemDto.isApi_done();
		
		// 1.토큰검사
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

		// user_number 값 지정하기
		// 토큰에서 이메일 > 이메일에서 userInfo Dto > 에서 user_number가져오기
		String email = jwtService.getUserEmail(token);
		MemberDto memberdto = memberService.userInfo(email);
		codeAPIDto.setUser_number(memberdto.getUser_number());
		
		if(memberdto.getUser_number() != codeAPIService.getSolvedProblemInfo(solved_problem_number).getUser_number()) {
			CodeAPIResultDto rtnNull = new CodeAPIResultDto();
			return new ResponseEntity<CodeAPIResultDto>(rtnNull, HttpStatus.UNAUTHORIZED);
		}
			
		if(api_done) {

			String correct_Java_code = codeAPIService.getCorrectJavaCode(codeAPIDto.getProblem_number()); // 자바 정답 코드
			String correct_Python_code = codeAPIService.getCorrectPythonCode(codeAPIDto.getProblem_number()); // 파이썬 정답 코드
			int image_number = codeAPIService.getImgNumber(codeAPIDto.getProblem_number()); // 해설 이미지 개수
			
			// 프론트로 반환할 값 지정
			CodeAPIResultDto codeAPIResultDto = new CodeAPIResultDto();
			codeAPIResultDto.setAnswer(solvedProblemDto.isSolved_problem_correct());
			
			if(codeAPIDto.getLanguage().equals("java"))
				codeAPIResultDto.setCorrect_code(correct_Java_code); //
			else
				codeAPIResultDto.setCorrect_code(correct_Python_code);
			
			codeAPIResultDto.setCorrect_output(solvedProblemDto.getCorrect_output());
			codeAPIResultDto.setError(null);
			codeAPIResultDto.setImg_number(image_number);
			codeAPIResultDto.setMy_code(codeAPIDto.getScript());
			codeAPIResultDto.setMy_output(solvedProblemDto.getMy_output());
			
			status = HttpStatus.OK;
			return new ResponseEntity<CodeAPIResultDto>(codeAPIResultDto, status);
		}
		
		//user number와 problem number로  최신code의 PK가져오기
//		int user_number = codeAPIDto.getUser_number();
//		int problem_number = codeAPIDto.getProblem_number();
//		int solved_problem_number = codeAPIService.getSolvedProblemNumber(codeAPIDto);
		codeAPIDto.setScript(solvedProblemDto.getSolved_problem_content());
		codeAPIDto.setLanguage(solvedProblemDto.getLanguage());
		

		// 5개의 테스트케이스 채점 >> 2.input, output 가져오기, 3.API 결과 가져오기 - TC 1 기준, 4.API결과,
		// output파일 비교(채점하기)
		String input = "";
		String output = "";
		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		String user_output = "";
		boolean flag = true; // 5개의 테스트케이스 통과 여부		
			
//TC1
		System.out.println("===========[tc1]============");
		// 2.input, output 가져오기
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

		// 3.API 결과 가져오기 - TC 1 기준
		codeAPIDto.setUser_input(input); // DB에 있는 input 파일로 설정
		codeAPIResponseDto = new CodeAPIResponseDto();
		System.out.println(codeAPIDto);
		try {
			codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 문제 번호, 사용자번호, 사용언어, 사용자코드
			System.out.println("채점 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[정답]"+codeAPIService.getOutput1(codeAPIDto.getProblem_number()).trim());
		// 4.API결과, output파일 비교(채점하기)
		user_output = codeAPIResponseDto.getOutput();
		System.out.println(codeAPIResponseDto);
		System.out.println(user_output);
		user_output = user_output.trim();
		if (output.trim().equals(user_output)) {
			codeAPIResponseDto.setAnswer(true); // DB output과 사용자 코드 실행 결과가 일치 - 정답
		} else {
			System.out.println("TC1 통과못함");
			codeAPIResponseDto.setAnswer(false); // DB output결과와 사용자 코드 실행 결과가 불일치 - 정답아님
			codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			flag = false;
		}

//TC2
		if(flag) {
			System.out.println("===========[tc2]============");
			// 2.input, output 가져오기
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
			System.out.println("[정답]"+codeAPIService.getOutput2(codeAPIDto.getProblem_number()).trim());
			// 3.API 결과 가져오기 - TC 2 기준
			codeAPIDto.setUser_input(input); // DB에 있는 input 파일로 설정
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 문제 번호, 사용자번호, 사용언어, 사용자코드
				System.out.println("채점 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4.API결과, output파일 비교(채점하기)
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true); // DB output과 사용자 코드 실행 결과가 일치 - 정답
			} else {
				flag = false;
				System.out.println("TC2 통과못함");
				codeAPIResponseDto.setAnswer(false); // DB output결과와 사용자 코드 실행 결과가 불일치 - 정답아님
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
			
		}

// TC3
		if(flag) {
			System.out.println("===========[tc3]============");
			// 2.input, output 가져오기
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
			
			System.out.println("[정답]"+codeAPIService.getOutput3(codeAPIDto.getProblem_number()).trim());
			// 3.API 결과 가져오기 - TC 3 기준
			codeAPIDto.setUser_input(input); // DB에 있는 input 파일로 설정
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 문제 번호, 사용자번호, 사용언어, 사용자코드
				System.out.println("채점 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4.API결과, output파일 비교(채점하기)
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true); // DB output과 사용자 코드 실행 결과가 일치 - 정답
			} else {
				flag = false;
				System.out.println("TC3 통과못함");
				codeAPIResponseDto.setAnswer(false); // DB output결과와 사용자 코드 실행 결과가 불일치 - 정답아님
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
		}

// TC4
		if(flag) {
			System.out.println("===========[tc4]============");
			// 2.input, output 가져오기
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
			
			System.out.println("[정답]"+codeAPIService.getOutput4(codeAPIDto.getProblem_number()).trim());
			// 3.API 결과 가져오기 - TC 4 기준
			codeAPIDto.setUser_input(input); // DB에 있는 input 파일로 설정
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 문제 번호, 사용자번호, 사용언어, 사용자코드
				System.out.println("채점 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4.API결과, output파일 비교(채점하기)
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true); // DB output과 사용자 코드 실행 결과가 일치 - 정답
			} else {
				flag = false;
				System.out.println("TC4 통과못함");
				codeAPIResponseDto.setAnswer(false); // DB output결과와 사용자 코드 실행 결과가 불일치 - 정답아님
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
		}

// TC5
		if(flag) {
			System.out.println("===========[tc5]============");
			// 2.input, output 가져오기
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
			
			System.out.println("[정답]"+codeAPIService.getOutput5(codeAPIDto.getProblem_number()).trim());
			// 3.API 결과 가져오기 - TC 5 기준
			codeAPIDto.setUser_input(input); // DB에 있는 input 파일로 설정
			codeAPIResponseDto = new CodeAPIResponseDto();
			try {
				codeAPIResponseDto = codeAPIService.codeTest(codeAPIDto); // 문제 번호, 사용자번호, 사용언어, 사용자코드
				System.out.println("채점 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4.API결과, output파일 비교(채점하기)
			user_output = codeAPIResponseDto.getOutput();
			user_output = user_output.trim();
			if (output.trim().equals(user_output)) {
				codeAPIResponseDto.setAnswer(true); // DB output과 사용자 코드 실행 결과가 일치 - 정답
			} else {
				flag = false;
				System.out.println("TC5 통과못함");
				codeAPIResponseDto.setAnswer(false); // DB output결과와 사용자 코드 실행 결과가 불일치 - 정답아님
				codeAPIResponseDto.setError(codeFeedbackService.findError(codeAPIResponseDto, codeAPIDto.getLanguage()));
			}
			
			//정답일 경우
			if(flag) {
				System.out.println("정답");
				codeAPIResponseDto.setAnswer(true);
				try {
					codeAPIService.updateCorrect(codeAPIDto.getProblem_number());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		// 5.컴파일 에러가 났다면 채점결과분석 
		// 나중에 구현 => 각 tc별 작성
		// codeAPIResponseDto.setError("에러분석결과");

		// 6.문제푼 결과 DB에 업데이트
		solvedProblemDto.setSolved_problem_correct(codeAPIResponseDto.isAnswer()); // 정답 여부
		solvedProblemDto.setApi_done(true);
		solvedProblemDto.setMy_output(user_output);
		solvedProblemDto.setCorrect_output(output);
		
		
		String correct_Java_code = codeAPIService.getCorrectJavaCode(codeAPIDto.getProblem_number()); // 자바 정답 코드
		String correct_Python_code = codeAPIService.getCorrectPythonCode(codeAPIDto.getProblem_number()); // 파이썬 정답 코드
		int image_number = codeAPIService.getImgNumber(codeAPIDto.getProblem_number()); // 해설 이미지 개수
		
		// 프론트로 반환할 값 지정
		CodeAPIResultDto codeAPIResultDto = new CodeAPIResultDto();
		codeAPIResultDto.setAnswer(codeAPIResponseDto.isAnswer());
		
		if(codeAPIDto.getLanguage().equals("java"))
			codeAPIResultDto.setCorrect_code(correct_Java_code); //
		else
			codeAPIResultDto.setCorrect_code(correct_Python_code);
		
		codeAPIResultDto.setCorrect_output(output);
		codeAPIResultDto.setCpuTime(codeAPIResponseDto.getCpuTime());
		codeAPIResultDto.setError(codeAPIResponseDto.getError());
		codeAPIResultDto.setImg_number(image_number);
		codeAPIResultDto.setMemory(codeAPIResponseDto.getMemory());
		codeAPIResultDto.setMy_code(codeAPIDto.getScript());
		codeAPIResultDto.setMy_output(user_output);
		codeAPIResultDto.setStatusCode(codeAPIResponseDto.getStatusCode());

		try {
			codeAPIService.updateSolvedProblem(solvedProblemDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 7.사용자에게 결과값 리턴(아직까지는 실행결과만 그대로 리턴 _ 컴파일 에러가 났으면 에러난 그대로 리턴)
		status = HttpStatus.OK;
		return new ResponseEntity<CodeAPIResultDto>(codeAPIResultDto, status);
	}
}