package com.ssafy.codackji.model.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.codackji.model.CodeAPIDto;
import com.ssafy.codackji.model.CodeAPIResponseDto;
import com.ssafy.codackji.model.SolvedProblemDto;
import com.ssafy.codackji.model.mapper.CodeAPIMapper;
import com.ssafy.util.JSONparsing;

@Service
public class CodeAPIServiceImpl implements CodeAPIService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public String getInput1(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getInput1(problem_number);
	}

	@Override
	public String getInput2(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getInput2(problem_number);
	}

	@Override
	public String getInput3(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getInput3(problem_number);
	}

	@Override
	public String getInput4(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getInput4(problem_number);
	}

	@Override
	public String getInput5(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getInput5(problem_number);
	}

	@Override
	public String getOutput1(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getOutput1(problem_number);
	}

	@Override
	public String getOutput2(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getOutput2(problem_number);
	}

	@Override
	public String getOutput3(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getOutput3(problem_number);
	}

	@Override
	public String getOutput4(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getOutput4(problem_number);
	}

	@Override
	public String getOutput5(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getOutput5(problem_number);
	}

	@Override
	public boolean updateSubmit(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).updateSubmit(problem_number) == 1;
	};

	@Override
	public boolean updateCorrect(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).updateCorrect(problem_number) == 1;
	};

	@Override
	public void addSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception {
		sqlSession.getMapper(CodeAPIMapper.class).addSolvedProblem(solvedProblemDto);
	}

	@Override
	public String getLanguageVersion(String name) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getLanguageVersion(name);
	}

	@Override
	public CodeAPIResponseDto codeTest(CodeAPIDto codeAPIDto) throws Exception {
		// 요청 url
		String url = "https://api.jdoodle.com/v1/execute";
		String input = codeAPIDto.getUser_input();
		if(input.equals("")||input==null) input = getInput1(codeAPIDto.getProblem_number());
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("clientId", "43a27528b836136335df7e0d4472a5f1");
		map.put("clientSecret", "6fab09897bfb0a8cfc6f1e9daf1d2af2cafb434a774690a5692c6c9a5d6a6ccc");
		map.put("script", codeAPIDto.getScript());
		map.put("stdin", input);
		map.put("language", codeAPIDto.getLanguage());

		String version = getLanguageVersion(codeAPIDto.getLanguage());
		map.put("versionIndex", version);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		if (response.getStatusCode() == HttpStatus.OK) {
			JSONparsing jsonParsing = new JSONparsing();
			JSONObject jsonObject = jsonParsing.stringToJson(response.getBody());

			Long l = (Long) jsonObject.get("statusCode");
			String str = Long.toString(l);

			codeAPIResponseDto.setOutput((String) (jsonObject.get("output")));
			codeAPIResponseDto.setStatusCode(str);
			codeAPIResponseDto.setMemory((String) (jsonObject.get("memory")));
			codeAPIResponseDto.setCpuTime((String) (jsonObject.get("cpuTime")));

		} else {
			System.out.println("Request Failed");
		}

		return codeAPIResponseDto;
	}

	@Override
	public String getCorrectJavaCode(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getCorrectJavaCode(problem_number);
	}

	@Override
	public String getCorrectPythonCode(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getCorrectPythonCode(problem_number);
	}

	@Override
	public int getImgNumber(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getImgNumber(problem_number);
	}

	@Override
	public int getSolvedProblemNumber(CodeAPIDto codeAPIDto) throws SQLException {
		return sqlSession.getMapper(CodeAPIMapper.class).getSolvedProblemNumber(codeAPIDto);
	}

	@Override
	public boolean updateSolvedProblem(SolvedProblemDto solvedProblemDto) throws SQLException {
		return sqlSession.getMapper(CodeAPIMapper.class).updateSolvedProblem(solvedProblemDto) == 1;
	}

	@Override
	public SolvedProblemDto getSolvedProblemInfo(int solved_problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getSolvedProblemInfo(solved_problem_number);
	}

	@Override
	public List<String> getAnalysisImage(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getAnalysisImage(problem_number);
	}
}
