package com.yedam.app.dept.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.dept.service.DeptService;
import com.yedam.app.dept.service.DeptVO;

@Controller
public class DeptController {
	private DeptService deptService;

	@Autowired
	public DeptController(DeptService deptService) {
		this.deptService = deptService;
	}

	// # 전체 조회
	@GetMapping("deptList")
	private String deptList(Model model) {
		List<DeptVO> list = deptService.deptList();

		model.addAttribute("depts", list);
		System.out.println(model);
		return "dept/list";
	}

	// # 단건 조회
	@GetMapping("deptInfo")
	private String deptInfo(DeptVO deptVO, Model model) {
		DeptVO findVO = deptService.deptInfo(deptVO);

		model.addAttribute("dept", findVO);
		System.err.println(model);

		return "dept/info";
	}

	// # 등록 화면 페이지
	@GetMapping("deptInsert")
	private String deptInsertForm() {
		return "dept/insert";
	}

	// # 등록 처리 페이지
	@PostMapping("deptInsert")
	private String deptInsertProcess(DeptVO deptVO) {
		int did = deptService.deptInsert(deptVO);

		String url = null;
		if (did > -1) {
			// 정상적으로 등록된 경우
			url = "redirect:deptInfo?departmentId=" + did;
		} else {
			// 등록되지 않은 경우
			url = "redirect:deptList";
		}
		return url;
	}

	// # 수정 페이지 화면
	@GetMapping("deptUpdate")
	private String deptUpdateForm(DeptVO deptVO, Model model) {
		DeptVO findVO = deptService.deptInfo(deptVO);
		model.addAttribute("dept", findVO);
		return "dept/update";
	}

	// # 수정 처리 페이지
	@PostMapping("deptUpdate")
	@ResponseBody
	private Map<String, Object> deptUpdateJASON(@RequestBody DeptVO deptVO) {
		return deptService.deptUapdate(deptVO);
	}
	
	// # 삭제
	@GetMapping("deptDelete")
	private String deptDelete(Integer departmentId) {
		deptService.deptDelete(departmentId);
		return "redirect:deptList";
	}

	

}
