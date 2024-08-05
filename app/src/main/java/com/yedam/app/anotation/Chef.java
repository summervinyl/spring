package com.yedam.app.anotation;

import org.springframework.stereotype.Component;

@Component //빈으로 등록하겠다는 선언
public class Chef {
	public void cooking() {
		System.out.println("Spring anotation 방식");		
	}
}
