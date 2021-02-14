package io.openvidu.js.java.model.service;

import org.springframework.stereotype.Service;

import io.openvidu.js.java.model.CodeAPIResponseDto;


@Service
public class CodeFeedbackServiceImpl implements CodeFeedbackService {

	@Override
	public String findError(CodeAPIResponseDto codeAPIResponseDto, String language) {
		String output = codeAPIResponseDto.getOutput();
		
		//java, python으로 나눠서 에러별 메서드 호출
		if(language.equals("java")) {
			if(codeAPIResponseDto.getCpuTime() == null) {//컴파일에러 or 무한루프
				if(output.contains("infinite loop")) {
					return infiniteLoop(output);
				}else{
					return compileError(output);
				}
			}else {//런타임 에러
				if(output.contains("ArithmeticException")) {
					return arithmeticException(output);
				}else if(output.contains("NullPointerException")) {
					return nullPointerException(output);
				}else if(output.contains("ArrayIndexOutOfBoundsException")){
					return arrayIndexOutOfBoundsException(output);
				}
			}		
		}else if(language.equals("python3")) {
			if(output.contains("IndentationError")) {
				return pythonIndentationError(output);
			}else if(output.contains("TabError")) {
				return pythonTabError(output);
			}else if(output.contains("SyntaxError")){
				return pythonSyntaxError(output);
			}else if(output.contains("ZeroDivisionError")){
				return pythonZeroDivisionError(output);
			}else if(output.contains("IndexError")) {
				return pythonIndexError(output);
			}else if(output.contains("NameError")) {
				return pythonNameError(output);
			}else if(output.contains("AttributeError")){
				return pythonAttributeError(output);
			}else if(output.contains("TypeError")) {
				return pythonTypeError(output);
			}
		}
		return "simpleerror";
	}

	private String pythonTypeError(String output) {
		int first = output.indexOf("line");
		int second = output.indexOf(",",first);
		String errorRow = output.substring(first+5, second);
		return errorRow+"번째 줄에서\n맞지 않는 타입을 사용하거나, 혼용할 수 없는 타입 간의 연산이 발생했어!\n형변환으로 해결하거나 잘못된 타입을 사용하고 있는지 확인해봐~";
	}

	private String pythonAttributeError(String output) {
		int first = output.indexOf("line");
		int second = output.indexOf(",",first);
		String errorRow = output.substring(first+5, second);
		int moduleFirst = output.indexOf("module '", second);
		int moduleSecond = output.indexOf("' has", moduleFirst);
		String moduleName = output.substring(moduleFirst+8, moduleSecond);
		return errorRow+"번째 줄에서\n모듈'"+moduleName+"'에 없는 속성을 사용했어!\n속성명을 다시 한 번 체크해볼래~? ";
	}

	private String pythonNameError(String output) {
		int first = output.indexOf("line");
		int second = output.indexOf(",",first);
		String errorRow = output.substring(first+5, second);
		int varFirst = output.indexOf("NameError", second);
		int varSecond = output.indexOf("' is");
		String varName = output.substring(varFirst+17, varSecond);
		return "혹시 "+errorRow+"번째 줄의\n변수명 '"+varName+"'이 잘못되지 않았어?\n오타가 났는지 혹은 선언되지 않은 변수를 사용하고 있는지 체크해봐~!";
	}

	private String pythonIndexError(String output) {//index out of range
		int first = output.indexOf("line");
		int second = output.indexOf(",",first);
		String errorRow = output.substring(first+5, second);
		return "이런~ 리스트의 크기와 같거나 큰 인덱스로 접근해서 IndexError가 발생했어!\n"+errorRow+"번째 줄에서\n인덱스가 초과되었는지 확인하러가자~";
	}

	private String pythonZeroDivisionError(String output) {//0으로 나눌 때
		int first = output.indexOf("line");
		int second = output.indexOf("\n",first);
		String errorRow = output.substring(first+5, second);
		return "어? "+errorRow+"번째 줄에서\n0으로 나누게 되어 발생한 에러야.\n나눗셈의 분모는 0이 될 수 없어!\n"
		+"어느 부분에서 0으로 나누게 되는지 생각해볼래~?";
	}

	private String pythonSyntaxError(String output) {//오타, 문법x
		int first = output.indexOf("line");
		int second = output.indexOf("\n",first);
		String errorRow = output.substring(first+5, second);
		
		if(output.contains("can't assign to keyword")) {
			return errorRow+"번째 줄에서\n예약어를 변수명으로 지정했어!\n예약어는 특정 기능을 수행하도록 미리 예약되어 있는 단어를 말해.\n"
					+ "따라서 예약어는 변수명으로 사용할 수 없어~\n다른 변수명으로 수정해볼까?";
		}
		return errorRow+"번째 줄에서\n문법 오류가 발생했어!\n이 줄을 다시 체크해볼까?";
	}

	private String pythonTabError(String output) {
		int first = output.indexOf("line");
		int second = output.indexOf("\n",first);
		String errorRow = output.substring(first+5, second);
		return "들여쓰기로 'tab'과 'space'를 혼용해서 사용할 때 발생하는 에러야.\n"+errorRow+"번째 줄을 점검해보고\n들여쓰기로 'tab'과 'space'중 하나를 선택해서 통일해보자!";
	}

	private String pythonIndentationError(String output) {
		int first = output.indexOf("line");
		int second = output.indexOf("\n",first);
		String errorRow = output.substring(first+5, second);
		if(output.contains("unexpected")) {
			return errorRow+"번째 줄에\n필요없는 들여쓰기가 있어서 오류가 발생했어!\npython에서는 들여쓰기로 코드가 적용될 구역을 정하기 때문에 주의해서 작성해야해!";
		}else if(output.contains("expected")){
			return errorRow+"번째 줄에\n들여쓰기가 되어있지 않아 오류가 발생했어!\npython에서는 들여쓰기로 코드가 적용될 구역을 정하기 때문에 주의해서 작성해야해!";
		}
		return errorRow+"번째 줄에\n잘못된 들여쓰기가 있어서 오류가 발생했어!\npython에서는 들여쓰기로 코드가 적용될 구역을 정하기 때문에 주의해서 작성해야해!";
	}

	private String arrayIndexOutOfBoundsException(String output) {
		int indexFirst = output.indexOf(":");
		int indexSecond = output.indexOf("at");
		String overIndex = output.substring(indexFirst+1, indexSecond-1);
		int first = output.indexOf("java:",indexSecond);
		int second = output.indexOf(")", first);
		int errorRow = Integer.parseInt(output.substring(first+5, second));
		return errorRow+"번째 줄에서\n배열의 크기를 넘어서는 인덱스 "+overIndex+"(을)를 참조해버렸어!\n"+
				"배열의 크기를 고려하여 인덱스를 다시 설정해줄래~?";
	}

	private String nullPointerException(String output) {
		int first = output.indexOf("java:");
		int second = output.indexOf(")", first);
		int errorRow = Integer.parseInt(output.substring(first+5, second));
		return "친구야!\n"+errorRow+"번째 줄에서  NULL값을 참조한 것 같아.\n해당 값이 NULL인지 확인해봐~";
	}

	private String arithmeticException(String output) {
		int first = output.indexOf("java:");
		int second = output.indexOf(")", first);
		int errorRow = Integer.parseInt(output.substring(first+5, second));
		return "앗!\n"+errorRow+"번째 줄에서\n0으로 나누게 되어 발생한 에러야.\n나눗셈의 분모는 0이 될 수 없어!\n"
				+"어느 부분에서 0으로 나누게 되는지 생각해볼래~?";
	}

	private String infiniteLoop(String output) {//무한루프
		return "무한루프가 발생했어!\nfor, while, do while 등 반복문을 다시 한 번 체크해보자.\n"
				+ "반복문을 빠져나올 종료조건이 잘 설정되어 있는지 다시 확인해볼래?";
	}

	private String compileError(String output) {
		int first = output.indexOf(":")+1;
		int second = output.indexOf(":", first);
		int errorRow = Integer.parseInt(output.substring(first, second));

		if(output.contains("}") && output.contains("parsing")) {// } 없을 때
			return "컴파일 에러야.\n"+errorRow+"번째 줄에\n닫는 중괄호 '}'를 추가해줘!";
		}else if(output.contains("')'")) {// ) 없을 때
			return "컴파일 에러야.\n"+errorRow+"번째 줄에\n닫는 소괄호 ')'를 추가해줘!";
		}else if(output.contains("initialized")) {// 초기화 해주지 않았을 때
			int variableIndex = output.indexOf("variable");
			int mightIndex = output.indexOf("might");
			String varName = output.substring((variableIndex+9),(mightIndex-1));
			return "컴파일 에러가 발생했어~\n"+errorRow+"번째 줄에서\n변수 "+varName+"에 접근하지만 초기화 되어있지 않은 상태야.\n"+varName+"변수를 선언한 곳으로 가서 초기화 해줘야 해~!";
		}else if(output.contains("';'")) {// 세미콜론 없을 때
			return "앗 컴파일 에러가 발생했네!\n"+ errorRow+"번째 줄에\n세미콜론';'이 빠졌어.\nJAVA의 경우 문장의 끝에는 꼭 ';'를 붙여주자!";
		}else if(output.contains("symbol")) {//선언하지 않은 변수 사용
			int variableIndex = output.indexOf("variable");
			int locationIndex = output.indexOf("location");
			String varName = output.substring((variableIndex+9),(locationIndex-3));
			return "존재하지 않는 변수 '"+varName+"'가  "+errorRow+"번째 줄에 등장했어.\n변수명을 잘못 입력했는지, 변수 선언을 빼먹었는지 다시 한 번 살펴볼래?";
		}else if(output.contains("not a statement")) {//잘못된 구문
			return errorRow+"번째 줄이\n잘못된 문법으로 작성되어서 컴파일 에러가 발생했어~\n착각하거나 잊은 JAVA 문법이 있는지 다시 점검해보자.";
		}
		return "컴파일 에러가 발생했어!";
	}
	
}
