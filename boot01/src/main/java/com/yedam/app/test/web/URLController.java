package com.yedam.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //스프링이 관리하는 bean 등록, web과 관련된 부분
public class URLController {
	//@RequestMapping(path="/test", method=RequestMethod.GET) //두가지 값 필요 = path, method
	@GetMapping("/test") //위 방법보다는 이 밥ㅂ법을 선호!
	@ResponseBody
	public String urlGetTest(String keyword) {
		//return "Server Response : Get Method\n Select - null";
		return "Server Response : Get Method\n Select - null" + keyword;
	}

}
