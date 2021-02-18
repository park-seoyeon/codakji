package io.openvidu.js.java.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

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
import io.openvidu.js.java.model.KakaoProfile;
import io.openvidu.js.java.model.MemberDto;
import io.openvidu.js.java.model.OAuthToken;
import io.openvidu.js.java.model.service.JwtServiceImpl;
import io.openvidu.js.java.model.service.MemberService;

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
	//public static final String REDIRECT_URI = "https://localhost:8000";
	//public static final String REDIRECT_URI = "https://i4a203.p.ssafy.io:8000";

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtServiceImpl jwtService;

	@PostMapping("/login/request")
	public ResponseEntity<Map<String, Object>> kakaoLoginRequest(@RequestBody String code) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

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
		} catch (IOException e) {
			e.printStackTrace();
		}

		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String password = "1234";

		MemberDto kakaoUser = new MemberDto(kakaoProfile.getKakao_account().getEmail(),
				kakaoProfile.getProperties().getNickname(), password, "kakao");

		Map<String, Object> resultMap = new HashMap<>();
		MemberDto originMemberDto;
		HttpStatus status = HttpStatus.OK;
		try {
			originMemberDto = memberService.userInfo(kakaoUser.getEmail());

			if (originMemberDto == null) {
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
			    
			    kakaoUser.setPassword(newPassword);
			    kakaoUser.setCertification(true);
				memberService.addUser(kakaoUser);
				kakaoUser = memberService.userInfo(kakaoUser.getEmail());
				
				if(!memberService.userInfo(kakaoUser.getEmail()).isProfile()) {
					try {
						URL url = new URL(kakaoProfile.getKakao_account().getProfile().getProfile_image_url());
						BufferedImage img = ImageIO.read(url);
						File file = new File("/home/ubuntu/codackji/profileImage/" + kakaoUser.getUser_number() + ".png");
						ImageIO.write(img, "png", file);
						
						byte [] image = new byte[ (int) file.length() ];
				        FileInputStream fis = new FileInputStream( file );
				        fis.read(image);
				        String base64EncodedImage = "data: image/png;base64," + new String (Base64.encodeBase64 (image), StandardCharsets.US_ASCII);
				        kakaoUser.setProfile_content(base64EncodedImage);
				        kakaoUser.setProfile(true);
						memberService.updateProfile(kakaoUser);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				return kakaoLogin(kakaoUser);
			} else if (originMemberDto.getOauth() != null && originMemberDto.getOauth().equals("kakao")) {
				return kakaoLogin(kakaoUser);
			} else {
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
				String token = jwtService.create("userid", loginUser.getEmail(), "access-token");

				memberDto.setToken(token);
				jwtService.setToken(memberDto);

				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				resultMap.put("userInfo", loginUser);
				resultMap.put("oauth-result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
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
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		MemberDto originMember = null;
		try {
			originMember = memberService.userInfo(memberDto.getEmail());
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
		    
		    originMember.setPassword(newPassword);
		    originMember.setCertification(true);
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
