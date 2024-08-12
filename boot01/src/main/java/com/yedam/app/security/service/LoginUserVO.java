package com.yedam.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter //회원정보 클래스와 정보전달 클래스 분리할 시엔 @Data 어노테이션 사용 금지, 세터 이용 금지
public class LoginUserVO implements UserDetails{
	
	private UserVO userVO;
	
	// 시큐리티 연동 처리할 경우 인증, 인가, 정보가 필요하다. - 로그인 정보 요청 쿼리문 작성 시 최소 필요한 정보다.
	// Collection : List의 부모 클래스
	// GrantedAuthority 인터페이스를 상속
	// extends제한을 거는 것 : GrantedAuthority를 상속한 것 중 하나
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority(userVO.getRoleName()));
		return auths;
	}

	@Override
	public String getPassword() {		
		return userVO.getPassword();
	}

	@Override
	public String getUsername() {
		return userVO.getLoginId();
	}
	
	// false면 계정 사용 불가
	// 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정 잠금 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 계정 패스워드 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정 사용 여부
	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
