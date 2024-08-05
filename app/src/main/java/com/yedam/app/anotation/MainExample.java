package com.yedam.app.anotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;


public class MainExample {

	public static void main(String[] args) {
		
		//인젝션 방식 변경 시, 메인은 놔두고, xml 파일에서 설정만 변경
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml"); // 인터페이스
		
		Restaurant res = (Restaurant) ctx.getBean(Restaurant.class);
		res.run();

	}

}
