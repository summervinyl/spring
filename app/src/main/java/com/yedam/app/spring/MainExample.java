package com.yedam.app.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainExample {

	public static void main(String[] args) {
		// 원래는 숨겨진 코드 : 컨테이너 생성 코드
		// 해당 컨테이너가 읽어야 하는 xml 주소를 매개 값으로 입력
		// classpath로 호출하지 않은 경우, 물리적 위치를 적어줘야 한다. file:src/main/resources/applicationContext.xml
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml"); // 인터페이스
		
		// id 속성이 있을 경우 id로 호출
		//  new 연산자를 사용하지 않고, 컨테이너에게 빈을 요청
		// (TV) 캐스팅을 해주지 않으면 객체만 리턴된다.
		TV tv = (TV) ctx.getBean("tv");
		tv.turnOn();
		
		// id 속성이 없을 경우 호출 방법
		TV subTv = (TV) ctx.getBean(TV.class); //TV.class TV에 대한 클래스 정보 그 자체를 리턴
		subTv.turnOn();
		
		//싱글톤
		//메모리 주소 비교
		//참조타입이면 같은 인스턴스인지 묻는다.
		//두개는 복사한 것이 아니고 각 각 불러낸 거지만 컨테이너 안에는 하나의 빈밖에 없어서 같은 대상이다.
		//내가 원하는 정보를 확실하게 제공받으려면 싱글톤이어야 한다.
		if(tv == subTv) {
			System.out.println("같은 빈입니다.");
		} else {
			System.out.println("다른 빈입니다.");
		}
	}

}