package com.ssafy.codackji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("MemberController V1")
@RestController
@RequestMapping("/user")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	@ApiOperation(value="회원가입", notes="회원가입 시작!", response=String.class)
	@PostMapping(value="/confirm/add")
	public ResponseEntity<String> addUser(@RequestBody MemberDto memberDto){
		System.out.println("[회원가입]"+memberDto.toString());
		
		if(memberService.emailCheck(memberDto.getEmail())>0) {
			System.out.println(">>중복된 아이디");
			return new ResponseEntity<String>("fail", HttpStatus.OK);
		}else {
			memberService.addUser(memberDto);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
	}

	
	
}
