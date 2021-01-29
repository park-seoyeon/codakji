package com.ssafy.codackji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.codackji.model.ProblemDto;
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
	
	@ApiOperation(value="단계별 문제 목록", notes = "해당 단계의 문제리스트를 반환한다", response = List.class)
	@GetMapping("/rank/{problem_rank}")
	public ResponseEntity<List<ProblemDto>> listProblem(@PathVariable("problem_rank") @ApiParam(value="문제 단계(난이도)", required = true) int problem_rank) throws Exception{
		System.out.println("[단계별 문제리스트]:"+problem_rank);
		return new ResponseEntity<List<ProblemDto>>(problemService.listProblem(problem_rank), HttpStatus.OK);
	}
	
	@ApiOperation(value="문제 보기", notes = "선택한 문제 보기", response = ProblemDto.class)
	@GetMapping("/{problem_number}")
	public ResponseEntity<ProblemDto> getProblem(@PathVariable("problem_number") @ApiParam(value="얻어올 문제 번호", required=true) int problem_number) throws Exception{
		System.out.println("[문제 보기]:"+problem_number);
		return new ResponseEntity<ProblemDto>(problemService.getProblem(problem_number), HttpStatus.OK);
	}
	
	
}
