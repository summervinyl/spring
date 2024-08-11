package com.yedam.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

@Controller // route : 사용자가 요청하는 부분(endpoint)과 그에 대한 처리
			// 컨트롤러의 역할 = endpoint : URL + METHOD => SERVICE => VIEW | ex) url + get 요청 ->
			// 서비스 연결 -> 화면 연결ㄴ
public class EmpController {
	// 컨트롤러는 서비스 연결, 그래서 해당 컨트롤러에서 제공하는 서비스 연결해야 해.
	private EmpService empService;

	@Autowired
	public EmpController(EmpService empService) {
		this.empService = empService;
	}

	// rest 방식 사용 안 할 거야.
	// get은 보안성 제로
	// get : 조회, 빈 페이지
	// post : 데이터 조회 (등록, 수정, 삭제)

	// # 전체 조회 : GET
	// 사용자에가 받아야 할 데이터는 없지만 전달해줘야 할 데이터는 있다. 그것을 Model이라고 한다.
	// Model = Request + Response
	@GetMapping("empList") // http://localhost:8099/yedam/empList
	private String empList(Model model) { // Response를 대신한다고 생각하면 된다.
		// 1) 해당 기능 수행하기.
		List<EmpVO> list = empService.empList();

		// 2) 클라이언트에 전달할 데이터 담기.
		model.addAttribute("emps", list);

		// 3) 데이터를 출력할 페이지 결정하기.
		return "emp/list";
		// 리턴 경로 앞에 / 절대 사용 금지
		// resolver는 리턴을 기반으로 prefix, suffix를 앞 뒤에 붙인다.
		// prefix : claspath:/templates/
		// suffix : .html
		// templates/emp/list.html
	}

	// # 단건 조회 : GET메소드를 이용할 때 QueryString을 사용해야 된다. QueryString (커맨드 객체, 리퀘스트 파람)
	// 정할 때는 객체냐 단일값이냐를 생각하고 결정.
	// 어노테이션이 없으면 커맨드 객체 : key=value 형태 / 결과를 보려면 http://localhost:8099/yedam/empInfo?employeeId=100
	@GetMapping("empInfo") // http://localhost:8099/empInfo
	private String empInfo(EmpVO empVO, Model model) { // 스프링이 알아서 VO를 만들어 준다.
		// 1) 해당 기능 수행하기.
		EmpVO findVO = empService.empInfo(empVO);

		// 2) 클라이언트에 전달할 데이터 담기.
		model.addAttribute("emp", findVO);

		// 3) 데이터를 출력할 페이지 결정하기.
		// templates/emp/info.html
		return "emp/info";
		// return "redirect:empList"; // 새로운 경로를 생성
	}

	// # 등록 - 페이지 : GET
	@GetMapping("empInsert")
	public String empInsertForm() {
		return "emp/insert";
	}

	// # 등록 - 처리 : POST => <form>를 통한 submit (JSON 불가, 커맨드 사용)
	// 화면에서 값을 넘겨줄 때 폼인지 아작스인지 생각하기. 받을 때는 단일인지
	@PostMapping("empInsert")
	public String empInsertProcess(EmpVO empVO) {
		// 성공 시 사원 번호 리턴, 실패 -1 리턴
		int eid = empService.empInsert(empVO);
		String url = null;
		if (eid > -1) {
			// 정상적으로 등록된 경우
			url = "redirect:empInfo?employeeId=" + eid;
		} else {
			// 등록되지 않은 경우
			url = "redirect:empList";
		}
		return url;
	}

	// # 수정 - 페이지 요청 : GET <=> 단건 조회 - 원래 데이터 보여주기
	// 단건 조회와 수정 페이지는 형태가 같아서 같은 페이지에서 진행하는 경우도 많음
	@GetMapping("empUpdate")
	public String empUpdateForm(EmpVO empVO, Model model) {
		EmpVO findVO = empService.empInfo(empVO);
		model.addAttribute("emp", findVO);
		return "emp/update";
	}

	// # 수정 - 처리 : AJAX 기반 => QeuryString으로 값 받기 -> 리턴 타입 위에 @ResponseBody 사용
	// 수정이 연속으로 일어나는 경우가 많아서 redirect보다는 아작스를 활용. submit을 사용
	// 데이터 처리 명령을 내려야 된다 -> ReponseBody
	//@PostMapping("empUpdate")
	@ResponseBody //AJAX 의미 - 리턴 타입 위에 붙는다.
	public Map<String, Object> empUpdateAJAXQueryString(EmpVO empVO) {
		return empService.empUpdate(empVO);
	}

	// # 수정 - 처리 : AJAX 기반 => JSON으로 값 받기 -> 매개변수에 @RequestBody 사용
	@PostMapping("empUpdate")
	@ResponseBody //AJAX 의미 - 리턴 타입 위에 붙는다.
	public Map<String, Object> empUpdateAJAXJSON(@RequestBody EmpVO empVO) { //매개변수 제이슨형태
		return empService.empUpdate(empVO);
	}
	

	// # 삭제 - 처리 : GET 방식을 더 많이 쓴다. 이유는 넘기는 데이터가 별로 없어서
	// 삭제는 아작스보다는 redirect 권장
	// 아작스를 사용하면 데이터를 보내고 성공 리턴이면 화면에서 삭제된 대상을 지워야 한다.
	@GetMapping("empDelete")
	public String empDelete(Integer employeeId) { // Integer 안에 필드가 존재하지 않음.
		empService.empDelete(employeeId);
		return "redirect:empList";
	}

}
