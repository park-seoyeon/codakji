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

import io.openvidu.js.java.model.MemberDto;
import io.openvidu.js.java.model.ProblemCommentDto;
import io.openvidu.js.java.model.service.JwtService;
import io.openvidu.js.java.model.service.ProblemCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("ProblemCommentController V1")
@RestController
@RequestMapping("/problem/comment")
public class ProblemCommentController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ProblemCommentService problemCommentService;

	@ApiOperation(value = "댓글 등록_토큰 검사를 한다", notes = "CREATE 새로운 댓글을 등록한다", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeProblemComment(
			@RequestBody @ApiParam(value = "토큰과 등록할 댓글 정보를 포함한 Dto", required = true) ProblemCommentDto problemCommentDto)
			throws Exception {
		String token = problemCommentDto.getToken();
		if (jwtService.isUsable(token)) {
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(jwtService.getUserEmail(token));
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				if (problemCommentService.writeProblemComment(problemCommentDto)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}

	///
	@ApiOperation(value = "댓글 불러오기_토큰 검사를 한다", notes = "RETRIEVE 댓글목록을 불러온다", response = String.class)
	@GetMapping("{problem_number}")
	public ResponseEntity<List<ProblemCommentDto>> listProblemComment(@PathVariable("problem_number") @ApiParam(value = "댓글 목록을 불러올 문제 번호", required = true) int problem_number)
			throws Exception {
			return new ResponseEntity<List<ProblemCommentDto>>(problemCommentService.listProblemComment(problem_number), HttpStatus.OK);
	}


	@ApiOperation(value = "댓글 수정_토큰 검사를 한다", notes = "UPDATE 댓글을 수정한다", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateProblemComment(
			@RequestBody @ApiParam(value = "토큰과 수정할 댓글 정보를 포함한 Dto", required = true) ProblemCommentDto problemCommentDto)
			throws Exception {
		String token = problemCommentDto.getToken();
		if (jwtService.isUsable(token)) {
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(jwtService.getUserEmail(token));
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				if (problemCommentService.updateProblemComment(problemCommentDto)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}

			}
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}

	@ApiOperation(value = "댓글 삭제_토큰 검사를 한다", notes = "DELETE 댓글을 삭제한다", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteArticle(
			@RequestBody @ApiParam(value = "토큰과 삭제할 댓글의 번호를 포함한 Dto.", required = true) ProblemCommentDto problemCommentDto)
			throws Exception {
		String token = problemCommentDto.getToken();
		if (jwtService.isUsable(token)) {
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(jwtService.getUserEmail(token));
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				if (problemCommentService.deleteProblemComment(problemCommentDto.getComment_number())) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}

	@ApiOperation(value = "좋아요 수정_토큰검사를 한다", notes = "UPDATE 각 댓글의 좋아요 수를 증가시키거나 감소시킨다.", response = String.class)
	@PutMapping("/likes")
	public ResponseEntity<String> updateLikes(
			@RequestBody @ApiParam(value = "토큰과 좋아요 수정 댓글 정보를 포함한 Dto", required = true) ProblemCommentDto problemCommentDto)
			throws Exception {
		String token = problemCommentDto.getToken();
		if (jwtService.isUsable(token)) {
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(jwtService.getUserEmail(token));
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				if (problemCommentService.updateLikes(problemCommentDto)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}
}
