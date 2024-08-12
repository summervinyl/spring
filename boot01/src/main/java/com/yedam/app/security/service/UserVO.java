package com.yedam.app.security.service;

import lombok.Data;

@Data
public class UserVO {
	private String loginId;
	private String password;
	private String roleName;

}
