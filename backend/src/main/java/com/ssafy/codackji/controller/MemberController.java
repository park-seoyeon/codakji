package com.ssafy.codackji.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.service.JwtServiceImpl;
import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.service.MemberService;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api("MemberController V1")
@RestController
@RequestMapping("/user")
public class MemberController {
	
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private MemberService memberService;

	@ApiOperation(value="회원가입", notes="회원가입 시작!", response=String.class)
	@PostMapping(value="/confirm/add")
	public ResponseEntity<String> addUser(@RequestBody MemberDto memberDto){
		System.out.println("[회원가입]"+memberDto.toString());
		
		if(memberService.emailCheck(memberDto.getEmail())>0) {
			System.out.println(">>중복된 아이디");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}else {
			memberService.addUser(memberDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		
	}
	
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/confirm/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				
				System.out.println("로그인 토큰 반환");
				
				String token = jwtService.create("userid", loginUser.getEmail(), "access-token");// key, data, subject				
				
				System.out.println("token:"+token);
				
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				System.out.println("토큰 반환 실패");
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "토큰인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{email}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("email") @ApiParam(value = "토큰 검사할 회원의 아이디(==이메일).", required = true) String email,
			HttpServletRequest request) {
//		logger.debug("userEmail : {} ", email);
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		System.out.println("token: " + request.getHeader("access-token"));
		
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(email);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
}
