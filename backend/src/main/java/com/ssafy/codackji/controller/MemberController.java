package com.ssafy.codackji.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.service.EmailService;
import com.ssafy.codackji.model.service.JwtServiceImpl;
import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.service.MemberService;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;



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
	
	@Autowired
	private EmailService emailService;

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
	
	@ApiOperation(value = "회원 정보_토큰검사를 한다", notes = "DB에 저장된 회원정보를 보여줍니다", response = MemberDto.class)
	@GetMapping("{email}")
	public ResponseEntity<MemberDto> userInfo(@PathVariable("email") @ApiParam(value = "정보를 얻어올 회원 아이디(==이메일)", required = true) String email, String token) throws Exception {
		
		if(jwtService.isUsable(token)){
			logger.info("userInfo - 호출");
			return new ResponseEntity<MemberDto>(memberService.userInfo(email), HttpStatus.OK);
		}	
		
		MemberDto nullMember = null;
		return new ResponseEntity<MemberDto>(nullMember, HttpStatus.OK);
	}	
	
	@ApiOperation(value = "회원정보 수정_토큰검사를 한다", notes = "회원 정보를 수정합니다-teach, name, password 변경", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody @ApiParam(value = "수정할 회원정보.", required = true) MemberDto memberDto, String token) throws Exception {
		logger.info("updateUser - 호출");
		
		if(jwtService.isUsable(token)) {
			if (memberService.updateUser(memberDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원정보 삭제_토큰검사를 한다", notes = "해당 아이디(==이메일)와 일치하는 회원 정보를 삭제한다", response = String.class)
	@DeleteMapping("{email}")
	public ResponseEntity<String> deleteUser(@PathVariable("email") @ApiParam(value = "삭제할 회원 아이디(==이메일)", required = true) String email, String token) throws Exception {
		logger.info("deleteUser - 호출");
		
		if(jwtService.isUsable(token)) {
			if (memberService.deleteUser(email)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}	
	
	@ApiOperation(value = "비밀번호 찾기", notes = "임시비밀번호를 생성해서 메일로 전송하고 암호화 후에는 DB에 저장한다. 메일이 존재하지 않거나 에러가 발생하면 fail을 리턴한다.", response = String.class)
	@PostMapping("/changepassword")
	public ResponseEntity<String> changePassword(
			@RequestBody @ApiParam(value = "아이디(==이메일)", required = true) MemberDto member){
		String email = member.getEmail();
		//우선 메일 계정이 존재하는지 체크
		if(memberService.emailCheck(email)>0) { //메일 계정이 존재함
		System.out.println("계정 있음");
		
			//비밀번호 생성 _ 난수 생성			
			int index = 0;
		    char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
		    'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
		    'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		    'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		 
		    StringBuffer sb = new StringBuffer();
		 
		    for (int i = 0; i < 7; i++) {
		        index = (int) (charArr.length * Math.random());
		        sb.append(charArr[index]);
		    }
		 
		    String password = sb.toString();
			
			//비밀번호 전송
			try {
				emailService.sendPassword(email, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>(FAIL, HttpStatus.OK);
			}
			
			//비밀번호 암호화 - 아래 테스트 코드 혹시 몰라서 남겨둡니다. 제출 시 삭제 예정	
			/*
			String newPassword = "";
			
			String oldPassword = "ssafy4a203";
			byte[] oldBytes = oldPassword.getBytes();
			
			String secret = "codackjia203";			
			byte[] key = secret.getBytes();
		        
			SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
		    try {
		            Mac mac = Mac.getInstance("HmacSHA256");
		            mac.init(secretKey);
		            newPassword =  Base64.encodeBase64String(mac.doFinal(oldBytes));
		    } catch (Exception ignored) {}
		    
		    System.out.println("newPassword:" + newPassword);
			*/
			
		    String newPassword = "";
			byte[] oldBytes = password.getBytes();
			
			String secret = "codackjia203";			
			byte[] key = secret.getBytes();
		        
			SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
		    try {
		            Mac mac = Mac.getInstance("HmacSHA256");
		            mac.init(secretKey);
		            newPassword =  Base64.encodeBase64String(mac.doFinal(oldBytes));
		    } catch (Exception ignored) {}
		    
		    System.out.println("암호화된 비밀번호:" + newPassword);			
			
					
			//암호화된 비밀번호 db에 저장
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(email);
			memberDto.setPassword(newPassword);
			
			try {
				memberService.updatePassword(memberDto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>(FAIL, HttpStatus.OK);
			}			
			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else { //메일 계정이 존재하지 않음			
			System.out.println("계정 없음");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
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
				resultMap.put("userInfo", loginUser);
				resultMap.put("oauth-result", SUCCESS);
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

	
	/* 토큰검사 관련해서 아래와 같이 안하기로 해서 일단 주석처리 - 제출 시 삭제예정
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
	*/

	@PostMapping("/confirm/kakaoLogin")
	public ResponseEntity<Map<String,Object>> kakaoLogin(@RequestBody MemberDto memberDto) {
		System.out.println("[카카오 로그인 요청]");
		memberDto.setOauth("kakao");
		//가입자 혹은 비가입자 체크해서 처리
		MemberDto originMemberDto;
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			originMemberDto = memberService.userInfo(memberDto.getEmail());
			if(originMemberDto==null) {
				System.out.println("[카카오 새회원 가입처리]");
				String addUserResult = addUser(memberDto).getBody();
				if(addUserResult.equals(SUCCESS)) {
					System.out.println("[카카오 자동 로그인]");
					return login(memberDto);
				}
			}else if(originMemberDto.getOauth()!=null && originMemberDto.getOauth().equals("kakao")){
				System.out.println("[기존 카카오 로그인 회원 - 로그인]");
				return login(memberDto);
			}else {
				System.out.println("[중복 로그인 회원]");
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				resultMap.put("oauth-result", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap,status);
	}
	
	@PostMapping("/confirm/continueKakaoLogin")
	public ResponseEntity<Map<String, Object>> continueKakaoLogin(@RequestBody MemberDto memberDto){
		System.out.println("[중복로그인 통합 및 로그인]");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		MemberDto originMember = null;
		try {
			originMember = memberService.userInfo(memberDto.getEmail());
			//수정할 내용: oauth=1 (추후 사진 추가 예정)
			originMember.setOauth("kakao");
			originMember.setPassword(memberDto.getPassword());
			if(memberService.updateUser(originMember)) {
				return login(originMember);
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();

		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

		
	}
	
}
