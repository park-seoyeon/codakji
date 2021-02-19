package io.openvidu.js.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.model.MeetingDto;
import io.openvidu.js.java.model.MemberDto;
import io.openvidu.js.java.model.service.JwtService;
import io.openvidu.js.java.model.service.MeetingService;
import io.openvidu.js.java.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("MeetingrController V1")
@RestController
@RequestMapping("/meeting")
public class MeetingController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value="전체 미팅룸 목록", notes = "전체 미팅룸 리스트를 반환한다", response = List.class)
	@PostMapping("/all")
	public ResponseEntity<List<MeetingDto>> allMeetingRooms(@RequestBody String token) throws Exception{
		List<MeetingDto> meetingRoomList = null;
		if(jwtService.isUsable(token) && jwtService.isInTime(token)) {
			String email = jwtService.getUserEmail(token);
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(email);
			memberDto.setToken(token);
			jwtService.setToken(memberDto);
			meetingRoomList = meetingService.allMeetingRooms();
			return new ResponseEntity<List<MeetingDto>>(meetingRoomList, HttpStatus.OK);
		}
		return new ResponseEntity<List<MeetingDto>>(meetingRoomList, HttpStatus.UNAUTHORIZED);
	}

}
