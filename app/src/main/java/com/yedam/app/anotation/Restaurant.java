package com.yedam.app.anotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //빈으로 등록하겠다는 선언
public class Restaurant {
	// chef를 필드로 가지는 Restaurant
	// 오타 주의
	
	// field
	private Chef chef;
	
	
	//생성자 injection
	@Autowired // 직접적으로 쓰고자 하는 대상 위에 @Autowired 선언해주면 된다.
	Restaurant(Chef chef) {
		this.chef = chef;
		System.out.println("생성자 인젝션");
	}
	
	
	// 세터 injection
	// 세터 방식으로 만들 때엔 반드시 기본 생성자 있어야 한다.
	Restaurant() {
		System.out.println("세터 인젝션");
	}	
	//@Autowired
	public void setChef(Chef chef) {
		this.chef = chef;		
	}
	
	
	// method
	public void run() {
		chef.cooking();		
	}
}
