package com.yedam.app.common.config;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // 모든 컨트롤러에 공통적으로 적용되는 기능 별도 관리 / 예외 처리 정의, 모든 컨트롤러가 공통적으로 가져야 되는 변수 등
public class WebAdvice {
	//@ExceptionHandler : 특정 예외에 대한 처리 등록
	//@ExceptionHandler(value = SQLException.class)
	public ResponseEntity<String> handleSqlException() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@ModelAttribute("contextPath")
	public String contextPath(HttpServletRequest req) { // 모든 페이지에서 공통으로 사용
		return req.getContextPath();		
	}
}
