package com.yedam.app.common.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice //컨트롤러에서 담당하는 예외 처리, 모든 컨트롤러가 공용으로 사용하는 변수 등록시에도 사용하는 방식
public class WebControllerAdvice {
	
	@ModelAttribute("contextPath") //모든 컨트롤러가 공용으로 사용하는 변수 등록
	public String contextPath(final HttpServletRequest req) {
		return req.getContextPath();
	}

}
