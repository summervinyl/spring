package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yedam.app.aop.service.AaaService;

@SpringBootTest
public class AopTests {
	@Autowired
	AaaService aaaService;
	
	//@Test
	void transcationalTest() {
		aaaService.insert();
	}
	
	@Autowired
	PasswordEncoder passwordEncoder; // interface
	
	@Test
	void pwdEnconderTest() {
		String password = "1234";
		
		// DB에 저장된 비밀번호는 반드시 암호화를 해줘야 한다.
		// PasswordEncoder가 갖고 있는 endcode를 사용하면 암호화가 된다.
		String enPwd = passwordEncoder.encode(password);
		System.out.println("---------------------------------------------------------------------------");
		System.err.println(enPwd); // 실행할 때마다 결과가 다르다.
		
		boolean result = passwordEncoder.matches(password, enPwd); // 매개변수 : 첫 번째는 사용자 비밀번호, 두 번째는 암호화된 비밀번호
		System.err.println(result);
	}

}
