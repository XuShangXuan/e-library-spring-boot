package com.esunbank.e_library_spring_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;

@Configuration
public class UsersConfig {

	@Bean
	@SessionScope
	public UsersInfoVo sessionUsersInfo() {
		return UsersInfoVo.builder().isLogin(false).loginMessage("").build();
	}
	
}
