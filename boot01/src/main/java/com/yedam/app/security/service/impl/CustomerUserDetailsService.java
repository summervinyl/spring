package com.yedam.app.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService{
	private UserMapper userMapper;
	
	@Autowired
	CustomerUserDetailsService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		// Mapper를 활용해서 DB에 접근
		// DB에 등록되어있는 정보 받아오기
		UserVO userVO = userMapper.getUserInfo(username);
		
		if(userVO == null) {
			throw new UsernameNotFoundException(username);
		}
		
		// 조회한 정보를 프로바이더에게 넘겨주기
		return new LoginUserVO(userVO); //VO를 감싼 ..를 리턴
	}

}
