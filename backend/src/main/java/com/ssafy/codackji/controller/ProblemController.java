package com.ssafy.codackji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.MemberDto;
import com.ssafy.codackji.model.ProblemDto;
import com.ssafy.codackji.model.ProblemStatDto;
import com.ssafy.codackji.model.SolvedProblemDto;
import com.ssafy.codackji.model.service.JwtService;
import com.ssafy.codackji.model.service.MemberService;
import com.ssafy.codackji.model.service.ProblemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("ProblemrController V1")
@RestController
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MemberService memberService;
	
	@ApiOperation(value="전체 문제 목록", notes = "전체 문제리스트를 반환한다", response = List.class)
	@GetMapping("/all")
	public ResponseEntity<List<ProblemDto>> allProblem() throws Exception{
		return new ResponseEntity<List<ProblemDto>>(problemService.allProblem(), HttpStatus.OK);
	}
	
	@ApiOperation(value="단계별 문제 목록", notes = "해당 단계의 문제리스트를 반환한다", response = List.class)
	@GetMapping("/rank/{problem_rank}")
	public ResponseEntity<List<ProblemDto>> listProblem(@PathVariable("problem_rank") @ApiParam(value="문제 단계(난이도)", required = true) int problem_rank) throws Exception{
		return new ResponseEntity<List<ProblemDto>>(problemService.listProblem(problem_rank), HttpStatus.OK);
	}
	
	@ApiOperation(value="문제 보기", notes = "선택한 문제 보기", response = ProblemDto.class)
	@GetMapping("/{problem_number}")
	public ResponseEntity<ProblemDto> getProblem(@PathVariable("problem_number") @ApiParam(value="얻어올 문제 번호", required=true) int problem_number) throws Exception{
		return new ResponseEntity<ProblemDto>(problemService.getProblem(problem_number), HttpStatus.OK);
	}
	
	@ApiOperation(value="유저의 문제풀이 통계를 반환한다.", notes ="난이도별 문제풀이현황, 정답률을 반환한다.", response = ProblemStatDto.class)
	@PostMapping("/user/stat")
	public ResponseEntity<ProblemStatDto> userProblemStat(@RequestBody @ApiParam(value="토큰", required=true) MemberDto memDto)
	throws Exception{
		String token = memDto.getToken();
		int user_number = memDto.getUser_number();
		ProblemStatDto psDto = new ProblemStatDto();
		
		if (jwtService.isUsable(token)) {
			String email = jwtService.getUserEmail(token);			
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
				
				List<SolvedProblemDto> userSolvedProblemList = problemService.userSolvedProblem(user_number);
				double rank1=0, rank2=0, rank3=0;
				for(SolvedProblemDto sp : userSolvedProblemList) {
					switch(sp.getProblem_rank()) {
					case 1:
						if(sp.isSolved_problem_correct())rank1++;
						break;
					case 2:
						if(sp.isSolved_problem_correct())rank2++;
						break;
					case 3:
						if(sp.isSolved_problem_correct())rank3++;
						break;
					}
				}
				double totalSolved = userSolvedProblemList.size();
				
				double total1 = problemService.getTotal(1);
				double total2 = problemService.getTotal(2);
				double total3 = problemService.getTotal(3);
				
				double d1 = (double)(rank1/total1) * 100;
				double d2 = (double)(rank2/total2) * 100;
				double d3 = (double)(rank3/total3) * 100;
				double ac = (double)((rank1 + rank2 + rank3)/totalSolved) * 100;

				psDto.setRank1((int)d1);
				psDto.setRank2((int)d2);
				psDto.setRank3((int)d3);
				psDto.setAccuracy((int)ac);
				
				return new ResponseEntity<ProblemStatDto>(psDto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<ProblemStatDto>(psDto, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ApiOperation(value = "유저가 푼 문제를 반환한다", notes = "Solved_Problem 테이블에서 user_number와 일치하는 정보를 가져온다.", response = MemberDto.class)
	@PostMapping("/user/solved")
	public ResponseEntity<List<SolvedProblemDto>> userSolvedProblem(@RequestBody @ApiParam(value = "토큰", required = true) MemberDto memDto)
			throws Exception {
		
		String token = memDto.getToken();

		List<SolvedProblemDto> nullList = null;
		
		if (jwtService.isUsable(token)) {
			String email = jwtService.getUserEmail(token);
			
			if (jwtService.isInTime(token)) {
				MemberDto memberDto = new MemberDto();
				memberDto.setEmail(email);
				memberDto.setToken(token);
				jwtService.setToken(memberDto);
						
				MemberDto memberdto = memberService.userInfo(email);
				int user_number = memberdto.getUser_number();

				return new ResponseEntity<List<SolvedProblemDto>>(problemService.userSolvedProblem(user_number), HttpStatus.OK);
			}
		}

		return new ResponseEntity<List<SolvedProblemDto>>(nullList, HttpStatus.UNAUTHORIZED);
	}
}
