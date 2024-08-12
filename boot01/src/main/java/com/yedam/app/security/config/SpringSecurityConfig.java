package com.yedam.app.security.config;

import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration // 설정이기 때문에
@EnableWebSecurity
public class SpringSecurityConfig {
	// 시큐리티 적용 2가지 방식

	// 패스워드 인코더 빈 - 패스워드 암호화
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // BCryptPasswordEncoder : 가장 일반적이고, 많이 쓰이는 PasswordEncoder의 구현 클래스
	}

	// 인증 및 인가 - 시큐리티 필터 체인 적용
	// 람다식 표현 - 자바 / 부트 3버전
	// 버전에 따라 차이가 있으니 주의해서 코딩하기.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Security가 적용될 URI
		http.authorizeHttpRequests((authrize)
				-> authrize
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/", "/all").permitAll()
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") //hasRole("USER") 하나의 접근 권한 추가, hasAnyRole- 여려개 중 최소 하나의 권한만 있어도 됨
																		 //user 페이지에 admin도 권한 주면 admin으로 로그인 후 user페이지 접근 가능!
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
		)
		.formLogin(formlogin -> formlogin
				.defaultSuccessUrl("/all"))
		.logout(logout -> logout
				.logoutSuccessUrl("/all")
				.invalidateHttpSession(true));
		
		return http.build();
	}
	
	// 메모리 방식으로 등록 / 일시적으로 등록 / 간단한 테스트를 위해 하는 거야.
	// 빌더 패턴
	/*
	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		// User 데이터 타입을 기반으로 인스턴스 생성을 할 때에, 인스턴스가 가져야하는 초기값 생성.
		UserDetails user = User.builder()
							   .username("user1")
							   .password(passwordEncoder().encode("1234"))
							   .roles("USER") // ROLE_USER -- roles을 사용하면 자신에게 들어오는 매개변수 앞에 ROLE_ 접두어가 붙는다.    // hasRole메소드로 선언했으면 이 메소드 사용
							 //.authorities("ROLE_USER")   -- roles와 같은 의미 같은 기능이지만 사용하는 메소드에 따라서 메소드가 달라진다.  // hasAuthority
							   .build();
		
		UserDetails admin = User.builder()
				   				.username("admin1")
				   				.password(passwordEncoder().encode("1234"))
				   			  //.roles("ADMIN") // ROLE_ADMIN
				   			    .authorities("ROLE_ADMIN") //-- 한 사람에게 여러개의 권한 부여 가능
				   			  //.authorities("ROLE_ADMIN", "ROLE_USER")
				   				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	} */
	//http://localhost:8099/yedam/user 로그인
	//http://localhost:8099/yedam/admin으로 접근하려면 로그아웃을 하고 admin으로 로그인
	//http://localhost:8099/yedam/logout
	
		
		
		/*
		 * // Security가 적용될 URI - 시큐리티 동작 경로 설정, 경로를 기반으로 움직이기 때문에! // requestMatchers :
		 * 특정 경로, 여러 방식 경로 설정 가능, 한번에 여러개의 경로에 대해서 권한 등록 작업 가능 // authenticated : 권한과
		 * 상관없이 누구인지 알 수 있으면.. 접근 가능! authenticated의 뜻 : 진짜임을 증명하다. // 순차적으로 진행
		 * http.authorizeHttpRequests((authrize) -> authrize
		 * .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() //FORWARD가 동작할 때
		 * 특별한 인증받지 않은 모든 사람에 대해서 오픈 동작하도록 하는 것
		 * .requestMatchers("/empList").authenticated() //특정 경로에 어떤 권한으로 처리할 건지 한 쌍으로
		 * 움직인다. - 최소한 누구인지는 알아야 한다. .anyRequest().permitAll() //anyRequest - 위에 정의되어있는
		 * 경우를 제외하고 나머지 모든 경로에 대해서 정의 - 다중 이프문에서 마지막 else문이라고 생각하면 된다. ) .formLogin();
		 * //기존 시큐리티를 커스터마이징을 위해 즉, 새로운 설정 새로운 빈 등록을 했을 떄에, 인증처리 formLogin를 명시해줘야 처리가
		 * 된다.
		 * 
		 * return http.build(); //지정한 경로(empList)를 제외한 나머지는 시큐리티 적용이 되지 않는다.
		 */
	

}
