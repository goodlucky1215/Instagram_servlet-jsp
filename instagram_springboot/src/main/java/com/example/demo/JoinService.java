package com.example.demo;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
	
	@Autowired(required=true)
	private MemberDao memberDao = null;

	public void join(MemberVO joinReq) throws SQLException {
		memberDao.insert(
					new Member(joinReq.getId(),joinReq.getName(),joinReq.getPassword(),new Date())
						);
	}
	public boolean checkid(String id) throws SQLException {
		Member member = null;
		member = memberDao.selectById(id); //이미 그 아이디가 존재하는지 확인
		if(member==null) { //이 아이디가 존재하지 않는 다는 것
			return true;
		}else return false;
	}
	
}
