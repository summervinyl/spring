package com.yedam.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //스프링이 관리하는 bean 등록, web과 관련된 부분
public class URLController {
	
	//경로가 같다하더라도 메소드(get,post)가 다르면 서버가 다르게 구분한다.
	
	//@RequestMapping(path="/test", method=RequestMethod.GET) //두가지 값 필요 = path, method
	@GetMapping("/test") //위 방법보다는 이 밥ㅂ법을 선호! //경로와 작업 지정
	@ResponseBody
	public String urlGetTest(String keyword) {
		//return "Server Response : Get Method\n Select - null";
		return "Server Response : Get Method\n Select - null " + keyword;
	}
	
	String hi = "hi";
	//@RequestMapping(path="/test", method=RequestMethod.POST)
	@PostMapping("/test")
	@ResponseBody
	public String urlPostTest(String hi) {
		return "Server Response : Post Method\n Select - null " + hi;
	}

}
