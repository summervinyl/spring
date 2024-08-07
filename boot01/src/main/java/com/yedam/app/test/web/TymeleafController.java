package com.yedam.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class TymeleafController {
	private EmpService empService;
	
	@GetMapping("tymeleaf")
	private String tymeleafTest(Model model) {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(100);
		
		EmpVO findVo = empService.empInfo(empVO);
		model.addAttribute("empInfo", findVo);
		return "test";
	}
}
