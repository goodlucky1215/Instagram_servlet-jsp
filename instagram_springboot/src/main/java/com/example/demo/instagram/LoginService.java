package com.example.demo.instagram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired(required=true)
	private MemberDao memberDao = null;

	public User login(String id, String password) throws SQLException {
		Member member = memberDao.selectById(id);
		if(member == null || password.equals(member.getPassword())==false) {
				throw new LoginFailException();
		}return new User(member.getId(),member.getName());
	}
	public List<Map<String, Object>> articleList(String memberId,String text) throws SQLException {
		List<Map<String, Object>> articles = new ArrayList<>();
		articles = memberDao.selectArticle(memberId,text);
		return articles;
	}
}
