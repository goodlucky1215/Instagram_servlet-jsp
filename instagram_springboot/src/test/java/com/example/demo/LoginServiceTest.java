package com.example.demo;

import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.example.demo.instagram.LoginService;
import com.example.demo.instagram.User;

class LoginServiceTest {
	LoginService loginService = new LoginService();
	@Test
	void 로그인() throws SQLException {
		//given : 이런게 주어져있는데
		String id = "lucky";
		String pw = "1";
		//when : 이런 상황일 때
		User user = loginService.login(id,pw);
		//then : 결과가 이런게 나와야 돼 
		assertThat(id).isEqualTo(user.getId());
	}
}
