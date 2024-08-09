package com.yedam.app.aop.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.yedam.app.emp.service.EmpVO;

@Aspect
@Component // AOP의 설정
public class CommonAspect {
	// 포인트 컷 (필터) : 조인 포인트 중에서 Advice(횡단관심, 부가기능)이 적용될 메소드 필터
	@Pointcut("within(com.yedam.app.emp.service.impl.*)") // 검색 조건 / 명시된 패키지 하위에 있는 비즈니스 메소드 총칭
	public void empPointCut() {
	} // 자기 위에 적용되어 있는 포인트 것을 불러오는 역할, 실행 블록은 비어있음.

	// Weaving : point cut + timing + Advice
	@Before("empPointCut()") // within이 일어나는 시점! Advice를 실행할 메소드 위에 선언!
	public void beforeAdvice(JoinPoint joinPoint) { // joinPoint = 비즈니스 서비스 메소드
		String sinagerStr = joinPoint.getSignature().toString(); // getSignature 메소드 가져오기
		Object[] args = joinPoint.getArgs(); // getArgs = 매개변수 가져오기
		System.err.println("########## 실행" + sinagerStr);
		for (Object arg : args) {
			System.err.println("매개변수");
			if (arg instanceof Integer) {
				System.err.println((Integer) arg); // 매개변수는 타입, 개수 제한이 없어서 object로 데이터를 받았다, 그리고 강제 캐스팅
			} else if (arg instanceof EmpVO) {
				System.err.println((EmpVO) arg);
			}
		}
	}

	// Around : 매개 변수로 조인 포인트를 넘겨받아서 직접 비즈니스 메소드 실행 코드를 입력해야 한다. 시작 시간과 끝난 시간 체크할 때 많이 사용.
	@Around("empPointCut()")
	private Object executeTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String sinagerStr = joinPoint.getSignature().toString();
		System.err.println("=== 시작 : " + sinagerStr);

		// 공통 기능
		System.err.println("=== 핵심 기능 전 실행 : " + System.currentTimeMillis());

		try {
			// 비즈니스 메소드를 실행
			Object obj = joinPoint.proceed(); // 어드바이스와 결합되는 위빙 작업을 거쳐야 한다. / proceed()를 쓰지 않으면 기능 실행 안 된다.
			return obj;
		} finally {
			// 공통 기능
			System.err.println("=== 핵심 기능 후 실행 : " + System.currentTimeMillis());
			System.err.println("=== 끝 : " + sinagerStr);
		}
	}
	
} // end of class
