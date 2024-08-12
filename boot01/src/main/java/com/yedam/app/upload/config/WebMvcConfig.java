package com.yedam.app.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// WebMvcConfigurer - 메소드가 있지만 추상 메소드가 아니고, 디폴트 메소드임(jdk 버전이 올라가면서 추상메소드 외 일반
	// 메소드 사용 가능하도록 업데이트 됨)
	// 인터페이스 내부에 선언되어 있지만 강제성이 없다. 그래서 설정하고 싶은 메소드만 오버라이드 하면 된다.
	// 스프링에서 지원하는 WebMvcConfigurer 인터페이스 구현, 부트가 갖고 있는 웹 환경을 오버라이드 하겠다. 하지 않으면 기본값이
	// 돌고 있다.
	// 웹 환경에 스프링 부트가 다이렉트로 접근할 수 있도록 경로 등록

	@Value("${file.upload.path}")
	private String uploadPath;

	// alt + shift + s -> override~어쩌고 클릭 후 원하는 메소드 클릭
	// 리소스 핸들링 - 별도의 경로 매핑 (리소스 매핑) - !파일접근하는거라서 파일다운로드시에는 따로 컨트롤러 생성하여야 한ㄷ.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { // 빈으로 들어오는 registry
		registry.addResourceHandler("/images/**").					 // URL - 리소스 핸들링으로 등록되는 정보와 src/main/resources/static의 하위 폴더명과 중복되지 않게 주의하기.
				 addResourceLocations("file:///" + uploadPath, "");  // 경로 mapping => 실제 경로
		// 서버에서 접근하는 ...를 컨트롤러를 거치지 않고 매핑하는 작업
		
		// 실제 파일 위치 D:\\upload\dex.jpg -> /images/dex.jpg로 바로 접근 가능
	}

}
