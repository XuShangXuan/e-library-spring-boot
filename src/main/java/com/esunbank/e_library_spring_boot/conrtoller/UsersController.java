package com.esunbank.e_library_spring_boot.conrtoller;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esunbank.e_library_spring_boot.dao.UsersInfoDao;
import com.esunbank.e_library_spring_boot.entity.Users;
import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/UsersController")
public class UsersController {

	private static Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Resource(name = "sessionUsersInfo")
	private UsersInfoVo usersInfo;
	
	@Autowired
	private HttpSession httpSession; 
	
	@Autowired
	private UsersInfoDao usersInfoDao;
	
	@ApiOperation(value = "圖書館-會員-檢查登入")
	@GetMapping(value = "/checkLogin")
	public ResponseEntity<UsersInfoVo> checkLogin() {
		
		logger.info("HttpSession checkLogin:" + httpSession.getId());
		logger.info("CheckLogin:" + usersInfo.toString());
		
		UsersInfoVo uesrs = UsersInfoVo.builder()
				.isLogin(usersInfo.getIsLogin())
				.loginMessage(usersInfo.getLoginMessage())
				.userID(usersInfo.getUserID())
				.userName(usersInfo.getUserName())
				.phoneNumber(usersInfo.getPhoneNumber())
				.lastLogin(usersInfo.getLastLogin())
				.build();
		
		return ResponseEntity.ok(uesrs);
	}
	
	@Transactional
	@ApiOperation(value = "圖書館-會員-登入")
	@PostMapping(value = "/login")
	public ResponseEntity<UsersInfoVo> login(@RequestBody UsersInfoVo inputUsers) {
		/*
			{
			  "phoneNumber": "0987654321",
			  "password": "123"
			}
			{
			  "phoneNumber": "0987654322",
			  "password": "123"
			}
		 */
		logger.info("HttpSession Login:" + httpSession.getId());
		logger.info("Before:" + usersInfo.toString());
		
		String inputPhoneNumber = inputUsers.getPhoneNumber();
		String inputPwd = inputUsers.getPassword();
		
		// Step2:依使用者所輸入的帳戶名稱取得 Users
		List<Users> dbUsers = usersInfoDao.findByPhoneNumber(inputPhoneNumber);
		
		Boolean isLogin = false;
		String loginMessage = null;
		Integer userID = null;
		String phoneNumber = null;
		String userName = null;

		if (dbUsers.size() !=0) { //檢查有無此帳號
			
			Users dbUser = dbUsers.get(0);
			
			int dbUserID = dbUser.getUserID();
			String dbPhoneNumber = dbUser.getPhoneNumber();
			String dbPwd = dbUser.getPassword();
			String dbUserName = dbUser.getUserName();

			if (dbPhoneNumber.equals(inputPhoneNumber) && dbPwd.equals(inputPwd)) { //檢查帳密是否正確

				LocalDateTime currentDateTime = LocalDateTime.now();
				
				// 更新最後登入時間
				dbUser.setLastLogin(currentDateTime);
				
				isLogin = true;
				loginMessage = "";
				phoneNumber = dbPhoneNumber;
				userName = dbUserName;

				usersInfo.setIsLogin(true);
				usersInfo.setLoginMessage("");
				usersInfo.setUserID(dbUserID);
				usersInfo.setPhoneNumber(dbPhoneNumber);
				usersInfo.setUserName(userName);
				usersInfo.setLastLogin(currentDateTime);
				
			} else {
				loginMessage = "帳號或密碼錯誤";
			}

		} else {
			loginMessage = "無此帳戶名稱,請重新輸入!";
		}
		
		logger.info("After:" + usersInfo.toString());
		
		UsersInfoVo outPutUsers = UsersInfoVo.builder()
				.isLogin(isLogin).loginMessage(loginMessage)
				.phoneNumber(phoneNumber).userName(userName).build();
		
		return ResponseEntity.ok(outPutUsers);
	}
	
	@ApiOperation(value = "圖書館-會員-登出")
	@GetMapping(value = "/logout")
	public ResponseEntity<UsersInfoVo> logout() {
		
		logger.info("HttpSession logout:" + httpSession.getId());
		
		usersInfo.setIsLogin(false);
		usersInfo.setLoginMessage("");
		usersInfo.setPhoneNumber(null);
		usersInfo.setUserName(null);
		
		UsersInfoVo usersInfoVo = UsersInfoVo.builder()
				.isLogin(false).loginMessage("")
				.build();
		
		return ResponseEntity.ok(usersInfoVo);
	}
	
	@ApiOperation(value = "圖書館-會員-註冊")
	@PostMapping(value = "/register")
	public ResponseEntity<UsersInfoVo> registerUser(@RequestBody UsersInfoVo registrationInfo) {
		
		/*
		{
		  "phoneNumber": "0123456789",
		  "password": "123",
		  "userName": "Ian"
		}
		{
		  "phoneNumber": "0123456788",
		  "password": "123",
		  "userName": "Josh"
		}
	    */
		
		// 獲取當前的日期和時間
        LocalDateTime currentDateTime = LocalDateTime.now();
		
		Users user = Users.builder()
				.phoneNumber(registrationInfo.getPhoneNumber())
				.password(registrationInfo.getPassword())
				.userName(registrationInfo.getUserName())
				.registrationTime(currentDateTime)
				.build();
				
		usersInfoDao.save(user);
		
		registrationInfo.setRegistrationTime(currentDateTime);
		
		return ResponseEntity.ok(registrationInfo);
	}
	
}
