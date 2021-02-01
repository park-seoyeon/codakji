package com.ssafy.codackji.controller;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.codackji.model.KakaoProfile;
import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.OAuthToken;
import com.ssafy.codackji.model.service.JwtServiceImpl;
import com.ssafy.codackji.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Api("KakaoLoginController V1")
@RestController
@RequestMapping("/kakao")
public class KakaoLoginController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	public static final String CLIENT_ID = "b01a0730a95c33f09f4655b0107ea8cc";
	public static final String REDIRECT_URI = "http://localhost:8080/";

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtServiceImpl jwtService;

//	@PostMapping("/login")
//	public void kakaoLogin(@RequestBody String code) {
//		System.out.println("코드:"+code);
//	}

	@PostMapping("/login/request")
	public ResponseEntity<Map<String, Object>> kakaoLoginRequest(@RequestBody String code) {
		System.out.println("[카카오 로그인]");
		// POST방식으로 Key=value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 -Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		///////////////////////////// 카카오에서 회원정보 받기/////////////////////////////////
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// Http 요청하기 -Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);
		System.out.println(response2.getBody());

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// User 오브젝트 : name, password, email
		System.out.println("=======[카카오에서 받은 회원정보]======");
		System.out.println("카카오 이메일:" + kakaoProfile.getKakao_account().getEmail());
		System.out.println("카카오 닉네임(이름):" + kakaoProfile.getProperties().getNickname());
		System.out.println("===============================");

		String password = "1234";

		MemberDto kakaoUser = new MemberDto(kakaoProfile.getKakao_account().getEmail(),
				kakaoProfile.getProperties().getNickname(), password, "kakao");

		Map<String, Object> resultMap = new HashMap<>();
		MemberDto originMemberDto;
		HttpStatus status = HttpStatus.OK;
		try {
			originMemberDto = memberService.userInfo(kakaoUser.getEmail());
			if (originMemberDto == null) {
				System.out.println("[카카오 새회원 가입처리]");
			    String newPassword = "";
				byte[] oldBytes = "kakao1234".getBytes();
				String secret = "codackjia203";			
				byte[] key = secret.getBytes();
				SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
			    try {
			            Mac mac = Mac.getInstance("HmacSHA256");
			            mac.init(secretKey);
			            newPassword =  Base64.encodeBase64String(mac.doFinal(oldBytes));
			    } catch (Exception ignored) {}
			    
			    System.out.println("암호화된 비밀번호:" + newPassword);
			    kakaoUser.setPassword(newPassword);
				memberService.addUser(kakaoUser);
				System.out.println("[카카오 자동 로그인]");
				return kakaoLogin(kakaoUser);
			} else if (originMemberDto.getOauth() != null && originMemberDto.getOauth().equals("kakao")) {
				System.out.println("[기존 카카오 로그인 회원 - 로그인]");
				return kakaoLogin(kakaoUser);
			} else {
				System.out.println("[중복 로그인 회원]");
				resultMap.put("userInfo", kakaoUser);
				resultMap.put("message", SUCCESS);
				resultMap.put("oauth-result", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> kakaoLogin(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.socialLogin(memberDto);
			if (loginUser != null) {

				System.out.println("로그인 토큰 반환");

				String token = jwtService.create("userid", loginUser.getEmail(), "access-token");// key, data, subject

				System.out.println("token:" + token);

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
	
	@PostMapping("/login/merge")
	public ResponseEntity<Map<String, Object>> mergeUserAndkakaoLogin(@RequestBody MemberDto memberDto){
		System.out.println("[중복로그인 통합 및 로그인]");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		MemberDto originMember = null;
		try {
			originMember = memberService.userInfo(memberDto.getEmail());
			//수정할 내용: oauth=1 (추후 사진 추가 예정)
			originMember.setOauth("kakao");
		    String newPassword = "";
			byte[] oldBytes = "kakao1234".getBytes();
			String secret = "codackjia203";			
			byte[] key = secret.getBytes();
			SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
		    try {
		            Mac mac = Mac.getInstance("HmacSHA256");
		            mac.init(secretKey);
		            newPassword =  Base64.encodeBase64String(mac.doFinal(oldBytes));
		    } catch (Exception ignored) {}
		    
		    System.out.println("암호화된 비밀번호:" + newPassword);
		    originMember.setPassword(newPassword);
			if(memberService.updateUser(originMember)) {
				return kakaoLogin(originMember);
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();

		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
