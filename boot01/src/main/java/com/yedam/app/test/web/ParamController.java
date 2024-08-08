package com.yedam.app.test.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.emp.service.EmpVO;

@CrossOrigin(origins = "*") // 해당 컨트롤러의 모든 요청 허용
@Controller // 컨트롤러는 웹을 처리하는 빈이 필요 // @Controller 빈 등록
public class ParamController {
	// Handler Adapter가 처리한다 ! - 실행시 req와 컨트롤러를 두고, 컨텐트 타입을 1차적으로 확인 -> 클라이언트가 보내준 컨텐트 타입을 비교해서 다르면 거부
	// Handler Adapter 리퀘스트에 있는 컨텐트타입과 호출해야하는 컨트롤러에 있는 매개변수를 비교해서 같은 대상이면 리퀘스트 파싱(데이터 변환) key=value&key=value의 형태를 key,value 단위로 자르고 실제 매개변수와 매칭
	// 이때 만약에 커맨드 객체면 내부에 필드를 검색, 단일값으로 되어있는 기본값이면 변수명으로 검색 그리고 컨트롤러에게 넘겨준다.
	// 커맨드객체와 리퀘스트파람을 혼합해서 사용한다. 같은 데이터 포맷을 쓰고 있기 때문에 에러 ㄴㄴ, 대신 변수명이 VO의 필드명과 같으면 안 된다.
	// Handler Adapter의 역할 : ex) list 출력을 해야 한다면 request에 값을 가져오고, VO에 담는 것까지
	
	// # QueryString을 처리하는 가장 기본적인 형태
	// QueryString (질의 문자열) : key=value&key=value&key=value...
	// Content-Type: Application/x-www-form-urlencode
	// Method : 구분하지 않음, 컨텐트 타입에 따라서 정해지는 게 있는데, 쿼리스트링은 get post 둘 다 가능
	// QueryString => 커맨드 객체 (어노테이션 사용하지 않음, 객체)
	// 쿼리스트링은 둘다 가능해서 겟메핑, 포스트매핑 쓰지 않을 거고 RequestMapping으로 사용하겠다.
	//@RequestMapping(path = "comobj", method = { RequestMethod.GET, RequestMethod.POST })
	//@ResponseBody
	public String commandObject(EmpVO empVO) { // EmpVO의 필드명들이 key가 된다.
		String result = "";
		result += "Path : /comobj \n";
		result += "\t employee_id " + empVO.getEmployeeId();
		result += "\t last_name " + empVO.getLastName();
		return result;
	}

	
	
	// # QueryString => @RequestParam : 기본타입, 단일값
	//@RequestMapping(path = "reqobj", method = { RequestMethod.GET, RequestMethod.POST })
	//@ResponseBody
	public String requestObject(@RequestParam Integer employeeId, // 값이 필수 - 핸들러 어댑터가 대응을 못함, error code : 400  
											  String lastName,    // 생략 가능
								@RequestParam(name = "message",              // name을 이용해서 이름 변경 가능
								              defaultValue = "no message",   // required = true인데 사용자가 혹시라도 값을 보내지 않으면 기본값 설정
								              required = true) String msg) { // true면 반드시 값을 입력 받아야 한다. // name 속성이 있다면 msg 말고 message를 이용해 값을 넘겨야 한다.
		// 매개값은 3개지만 employee_id만 입력해줘도 오류가 뜨지 않는다. 이유는 lastName은 @RequestParam을 생략하고 단일값을 쓰면 필수는 아니지만 생략 가능하다. msg는 required = true지만 defaultValue가 설정되어 있기 때문

		String result = "";
		result += "Path : /comobj \n";
		result += "\t employee_id " + employeeId;
		result += "\t last_name " + lastName;
		result += "\t message " + msg;
		return result;
	}
	
	
	// # PathVariable : 경로에 값을 포함하는 방식, 단일 값
	// 메소드 상관없음, 컨텐트-타입도 상관없음
	//@RequestMapping(path = "path/{id}") 		// ex) path/hong, path/123   //값이 누락 되면 error cod : 404
	//@ResponseBody
	public String pathVariable(@PathVariable String id) {
		String result = "";
		result += "Path : /path/{id} \n";
		result += "\t id : " + id;
		return result;
	}
	
	
	// # @RequestBody : JSON 포맷, 배열 or 객체
	// Method : 반드시 POST, PUT 사용
	// Content-Type : appliction/json
	// 이상한 값이 들어가도 에러는 나지 않고 버려진다.
	// 부메랑
	// { "employeeId" : "1000", "lastName" : "윤", "msg" : "안녕하세요." } => msg는 버려진다.
	// 1) @RequestBody : JSON 포맷 = 객체 형태로 보냄
	@RequestMapping("resbody")
	@ResponseBody
	public String requestBody(@RequestBody EmpVO empVO) {
		String result = "path : /resbody \n";
		result += "\t employee_id " + empVO.getEmployeeId();
		result += "\t last_name " + empVO.getLastName();
		return result;
	}
	// 2) @RequestBody : JSON 포맷 = 배열 형태로 보냄 / 사용자가 개발자에서 데이터를 JSON형태로 주어야 한다.
	// [{ "employeeId" : "1000", "lastName" : "윤" }]
	// [{ "employeeId" : "1000", "lastName" : "윤"}, { "employeeId" : "1000", "lastName" : "채" }]
	@RequestMapping("resbodyList")
	@ResponseBody
	public String requestBody(@RequestBody List<EmpVO> list) {
		String result = "path : /resbodyList \n";
		for(EmpVO empVO : list) {			
			result += "\t employee_id " + empVO.getEmployeeId();
			result += "\t last_name " + empVO.getLastName();
			result += "\n";
		}
		return result;
	}
	

}