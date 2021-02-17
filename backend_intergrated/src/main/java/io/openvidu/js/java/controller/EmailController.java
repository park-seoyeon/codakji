package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.model.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class EmailController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private EmailService emailService;

	@ApiOperation(value = "이메일 전송", notes = "이메일을 전송한다.", response = Map.class)
	@GetMapping("/sendEmail/{toAddress}")
	public ResponseEntity<Map<String, Object>> sendEmail(
			@PathVariable("toAddress") @ApiParam(value = "메일 주소", required = true) String toAddress) {
		//HttpServletRequest request
//		System.out.println("[sendEmail]");

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
			@PathVariable("userEmail") @ApiParam(value = "인증 처리되는 메일 주소", required = true) String userEmail) {
		//HttpServletRequest request
		
//		System.out.println("이메일 인증: " + userEmail); 
		
		try {
			if (emailService.updateCertification(userEmail)) {
				String htmlStr = "<div style='text-align: center; margin-top:10%;'><h2>성공적으로 인증되었습니다.</h2>"
						+"<img src = 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7AVpo%2FbtqUM75u9KM%2F6a6GmXy384zwyq8sdZgLQK%2Fimg.png'/>"
						+"<br>"
						//+"<a href='https://i4a203.p.ssafy.io:8000' style='text-decoration:none'><h2 style='color:blue'>코딱지로 이동하기</h2></a></div>";
				+"<a href='http://localhost:8080/' style='text-decoration:none'><h2 style='color:blue'>코딱지로 이동하기</h2></a></div>";
				return new ResponseEntity<String>(htmlStr, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);			
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);	
		
	}

}
