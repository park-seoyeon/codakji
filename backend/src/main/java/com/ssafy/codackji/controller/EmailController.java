package com.ssafy.codackji.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.codackji.model.service.EmailService;

import io.swagger.annotations.*;

@RestController
public class EmailController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private EmailService emailService;

	@ApiOperation(value = "이메일 전송", notes = "이메일을 전송한다.", response = Map.class)
	@GetMapping("/sendEmail/{toAddress}")
	public ResponseEntity<Map<String, Object>> sendEmail(
			@PathVariable("toAddress") @ApiParam(value = "메일 주소", required = true) String toAddress,
			HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			if (emailService.sendEmail(toAddress)) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

		
	@ApiOperation(value = "이메일 인증", notes = "인증하기 버튼을 누르면 인증이된다.")
	@GetMapping("/certify/{userEmail}")
	public ResponseEntity<String> certify(
			@PathVariable("userEmail") @ApiParam(value = "인증 처리되는 메일 주소", required = true) String userEmail,
			HttpServletRequest request) {
		
		System.out.println("이메일 인증: " + userEmail); 
		
		try {
			if (emailService.updateCertification(userEmail)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);			
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);	
		
	}

}