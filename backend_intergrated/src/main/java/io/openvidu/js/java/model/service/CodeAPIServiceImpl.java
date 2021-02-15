package io.openvidu.js.java.model.service;

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

import io.openvidu.js.java.model.CodeAPIDto;
import io.openvidu.js.java.model.CodeAPIResponseDto;
import io.openvidu.js.java.model.SolvedProblemDto;
import io.openvidu.js.java.model.mapper.CodeAPIMapper;
import io.openvidu.js.java.util.JSONparsing;

@Service
public class CodeAPIServiceImpl implements CodeAPIService {

	@Autowired
	private SqlSession sqlSession;

	// 테스트 케이스 input 파일 가져오기
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

	// 테스트 케이스 output 파일 가져오기
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

	// 문제 제출 횟수 증가시키기
	@Override
	public boolean updateSubmit(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).updateSubmit(problem_number) == 1;
	};

	// 문제 정답 수 증가시키기
	@Override
	public boolean updateCorrect(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).updateCorrect(problem_number) == 1;
	};

	// 문제 푼 결과 저장하기
	@Override
	public void addSolvedProblem(SolvedProblemDto solvedProblemDto) throws Exception {
		sqlSession.getMapper(CodeAPIMapper.class).addSolvedProblem(solvedProblemDto);
	}

	// 언어 버전 가져오기
	@Override
	public String getLanguageVersion(String name) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getLanguageVersion(name);
	}

	// API 실행
	@Override
	public CodeAPIResponseDto codeTest(CodeAPIDto codeAPIDto) throws Exception {
		System.out.println("들어옴");
		// 요청 url
		String url = "https://api.jdoodle.com/v1/execute";
		String input = codeAPIDto.getUser_input();
		if(input.equals("")||input==null) input = getInput1(codeAPIDto.getProblem_number());
		System.out.println("input : "+input);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// 전송할 JSON
		Map<String, Object> map = new HashMap<>();
		map.put("clientId", "43a27528b836136335df7e0d4472a5f1");
		map.put("clientSecret", "6fab09897bfb0a8cfc6f1e9daf1d2af2cafb434a774690a5692c6c9a5d6a6ccc");
		map.put("script", codeAPIDto.getScript()); // 실행할 코드
		map.put("stdin", input); // input 데이터
		map.put("language", codeAPIDto.getLanguage()); // 사용 언어

		// 사용 언어 버전 가져오기
		String version = getLanguageVersion(codeAPIDto.getLanguage());
		map.put("versionIndex", version); // 사용하는 언어의 JDoodle 버전

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		// API 응답 확인
		CodeAPIResponseDto codeAPIResponseDto = new CodeAPIResponseDto();
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
			System.out.println(response.getBody());

			JSONparsing jsonParsing = new JSONparsing();
			JSONObject jsonObject = jsonParsing.stringToJson(response.getBody());

			System.out.println("Output: ");
			System.out.println((String) jsonObject.get("output"));

			Long l = (Long) jsonObject.get("statusCode");
			String str = Long.toString(l);

			// codeAPIResponseDto 객체에 API결과 값 담기
			codeAPIResponseDto.setOutput((String) (jsonObject.get("output"))); // 코드 실행 결과 (올바른 실행결과 or 에러메세지 출력)
			codeAPIResponseDto.setStatusCode(str); // API 호출 결과
			codeAPIResponseDto.setMemory((String) (jsonObject.get("memory"))); // 메모리 사용량
			codeAPIResponseDto.setCpuTime((String) (jsonObject.get("cpuTime"))); // cpu 사용 시간

		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
		}

		return codeAPIResponseDto;
	}

	// Java 정답코드 가져오기
	@Override
	public String getCorrectJavaCode(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getCorrectJavaCode(problem_number);
	}

	// Python 정답코드 가져오기
	@Override
	public String getCorrectPythonCode(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getCorrectPythonCode(problem_number);
	}

	// 해설 이미지 개수 가져오기
	@Override
	public int getImgNumber(int problem_number) throws Exception {
		return sqlSession.getMapper(CodeAPIMapper.class).getImgNumber(problem_number);
	}

	// solvedProblemNumber를 가져오기
	@Override
	public int getSolvedProblemNumber(CodeAPIDto codeAPIDto) throws SQLException {
		return sqlSession.getMapper(CodeAPIMapper.class).getSolvedProblemNumber(codeAPIDto);
	}

	// SolvedProblem 테이블 업데이트하기
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

	// API 컴파일 에러 분석하기 - 머신러닝
	// @Override
	// 구현 예정

}
