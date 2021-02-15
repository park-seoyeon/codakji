package io.openvidu.js.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.model.NoticeDto;
import io.openvidu.js.java.model.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api("NoticeController V1")
@RestController
@RequestMapping("/notice")
public class NoticeController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	
	@Autowired
	private NoticeService noticeService;
	
	
	@ApiOperation(value="공지 등록", notes="새로운 공지를 등록합니다", response=String.class)
	@PostMapping
	public ResponseEntity<String> writeNotice(@RequestBody @ApiParam(value = "등록할 공지의 정보", required = true) NoticeDto noticeDto) throws Exception {

		if (noticeService.writeNotice(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	
	@ApiOperation(value="공지 불러오기", notes="공지목록을 불러옵니다", response=String.class)
	@GetMapping
	public ResponseEntity<List<NoticeDto>> listNotice() throws Exception {
		
		return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(), HttpStatus.OK);
	}
	
	@ApiOperation(value="공지 조회수 증가시키기", notes="조회 수를 증가시킵니다", response=String.class)
	@GetMapping("/views/{notice_number}")
	public ResponseEntity<String> updateViews(@PathVariable("notice_number") @ApiParam(value = "조회수를 증가시킬 공지의 번호.", required = true) int notice_number) throws Exception {
		
		if (noticeService.updateViews(notice_number)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
		
	
	@ApiOperation(value="공지 수정", notes="공지를 수정합니다", response=String.class)
	@PutMapping
	public ResponseEntity<String> updateNotice(@RequestBody @ApiParam(value = "수정할 공지의 정보", required = true) NoticeDto noticeDto) throws Exception {
			
		if (noticeService.updateNotice(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="공지 삭제", notes="공지를 삭제합니다", response=String.class)
	@DeleteMapping("{notice_number}")
	public ResponseEntity<String> deleteNotice(@PathVariable("notice_number") @ApiParam(value = "삭제할 공지의 번호.", required = true) int notice_number) throws Exception {
	
		if (noticeService.deleteNotice(notice_number)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}

