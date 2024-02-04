package com.esunbank.e_library_spring_boot.vo;

import java.time.LocalDateTime;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class UsersInfoVo {

	private Boolean isLogin;

	private String loginMessage;
	
	private int userID;
	
	private String phoneNumber;
	
	private String password;
	
	private String userName;
	
	private LocalDateTime registrationTime;
	
	private LocalDateTime lastLogin;

}
