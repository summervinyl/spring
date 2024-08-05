package com.yedam.app.javabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaBeanConfig {
	//
	//@Configuration를 설정하면 스프링이 해당 클래스에 있는 메소드를 순차적으로 모두 실행해서 컨테이너에 등록
	
	@Bean
	// 인스턴스를 반환하는 메소드 위에 @Bean 설정
	// 설정 => 메소드 실행
	public Chef chef() {
		return new Chef();	//해당 클래스의 인스턴스 생성 후 리턴
	}
	
	@Bean
	public Restaurant restaurant (Chef chef) {
		return new Restaurant(chef); //해당 클래스의 인스턴스 생성 후 리턴
	}

}
