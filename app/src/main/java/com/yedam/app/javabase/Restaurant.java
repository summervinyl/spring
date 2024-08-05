package com.yedam.app.javabase;

public class Restaurant {
	// chef를 필드로 가지는 Restaurant
	// 오타 주의
	
	// field
	private Chef chef;
	
	
	// 생성자 injection
	Restaurant(Chef chef) {
		this.chef = chef;
		System.out.println("생성자 인젝션");
	}
	
	
	// 세터 injection
	// 세터 방식으로 만들 때엔 반드시 기본 생성자 있어야 한다.
	Restaurant() {
		System.out.println("세터 인젝션");
	}	
	public void setChef(Chef chef) {
		this.chef = chef;		
	}
	
	
	// method
	public void run() {
		chef.cooking();		
	}
}
