package com.yedam.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

import lombok.AllArgsConstructor;


// 페이지 호출하는 controller
@Controller
@AllArgsConstructor
public class ThymeleafController {
	private EmpService empService;
	
	@GetMapping("tymeleaf")
	private String thymeleafTest(Model model) {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(100);
		
		EmpVO findVo = empService.empInfo(empVO);
		model.addAttribute("empInfo", findVo); // medel에 담은 값을 페이지로 전달. 그래서 수정 부분에선 안 쓰임.
		// 첫 번째 매개 변수가 변수명이 된다.
		
		return "test";
	}
}
