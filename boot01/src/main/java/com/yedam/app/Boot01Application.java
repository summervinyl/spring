package com.yedam.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 자동으로 생성되는 클ㄹ래스 : 함부로 위치를 옮기지도 말고, 추가 ㄴㄴ
// 중요한 클래스, auto 
// spring boot configuration
@SpringBootApplication
@MapperScan(basePackages = "com.yedam.app.**.mapper")
public class Boot01Application {

	public static void main(String[] args) {
		//SpringApplication 인스턴스 = 실행
		SpringApplication.run(Boot01Application.class, args);
	}

}
