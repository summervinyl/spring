package com.yedam.app.xml;

public class JavaMainExample {

	public static void main(String[] args) {
		Chef chef = new Chef();
		
		Restaurant res = new Restaurant(chef);
		//Restaurant res = new Restaurant(); -> NullPoingException
		//만약 스프링으로 진행하게 된다면 context initialization 오류 발생
		
		res.run();
	}

}
