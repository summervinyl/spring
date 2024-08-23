package com.yedam.app.common.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // 예외 처리 정의, 모든 컨트롤러가 공통적으로 가져야 되는 변수 등
public class WebAdvice {
	@ModelAttribute("contextPath")
	public String contextPath(HttpServletRequest req) {
		return req.getContextPath();		
	}
}
