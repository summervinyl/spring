package com.yedam.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

@RestController // @Controller + 모든 메소드에 @ResponsBody가 적용되었다는 선언
				// @RestController : AJAX
public class EmpRestController {

	private EmpService empService;

	@Autowired
	EmpRestController(EmpService empService) {
		this.empService = empService;
	}
	
	/* TEST : 부메랑 / http://127.0.0.1:8099/yedam/emps / BODY -> JSON
	 * # POST
	 * { "lastName" : "윤", "email" : "yoonn1217@gmail.com", "jobId" : "IT_PROG", "hireDate" : "2024-10-28" } : return = primary key 
	 * 
	 * # PUT - pom.xml update sql문에 lastName, jobId를 명시해뒀기 때문에 반드시 적어주어야 실행된다.
	 * { "firstName": "경민", "lastName": "윤", "jobId": "IT_PROG" }
	 */
	
	
	// Rest는 기본 통신 방식이 AJAX라서 페이지를 요구하지 않기 때문에 등록, 수정 컨트롤러 하나면 된다. 즉, 데이터만 가져온다.?!?!
	// 제공하는 서비스는 똑같지만, 사용자와 상호작용하는 방식이 달라진다.
	// 서비스를 method를 통해서 구분한다.

	// 기본 베이스는 PathVariable, JSON 사용.
	// 단, 필요한 경우엔 (검색과 같은 기능) QueryString 사용.

	
	// # 전체 조회 : GET ▶ employees
	@GetMapping("emps")
	public List<EmpVO> empList() {
		return empService.empList(); // 조회하고자 하는 결과를 리턴해주면 된다.
	}

	
	// # 단건 조회 : GET ▶ employees/employeeId
	// 내가 조회하고자 하는 데이터를 PathVariable을 통해서 값을 가져온다.
	// ? 뒤엔 데이터로 인식하고, / 뒤엔 경로로 인식한다.
	@GetMapping("emps/{eId}")
	public EmpVO empInfo(@PathVariable(name = "eId") Integer employeeId) {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(employeeId);
		return empService.empInfo(empVO);
	}

	
	// # 등록 : POST ▶ employees
	@PostMapping("emps")
	public int empInsert(@RequestBody EmpVO empVO) { // @RequestBody : JSON으로 데이터 받기.
		return empService.empInsert(empVO);
	}

	
	// # 수정 : PUT ▶ employees/employeeId
	// put 방식으로 경로를 설계할 때 수정하고자 하는 데이터를 경로에 명시할 수도 있고, 하지 않을 수도 있다. 마음대로!
	@PutMapping("emps/{employeeId}")
	public Map<String, Object> empUdate( @PathVariable Integer employeeId, // 경로를 통해서 수정할 타켓
										 @RequestBody EmpVO empVO) {	   // 수정할 정보는 JSON 포맷으로
		empVO.setEmployeeId(employeeId); 						           // 데이터가 따로 들어오기 때문에 합치는 작업.
		return empService.empUpdate(empVO);
	}
	
	// # 삭제 : DELETE ▶ employees/employeeId
	// 특정 대상을 삭제해야 하는 경우 경로(path)에 값이 명시되어야 한다.	
	@DeleteMapping("emps/{employeeId}")
	public Map<String, Object> empDelete(@PathVariable Integer employeeId) {
		return empService.empDelete(employeeId);
	}
	
} // end of class
