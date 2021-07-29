package member.command;

import java.sql.SQLException;
import java.util.Date;


public class JoinService {
	private MemberDao memberDao = null;
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public void join(MemberVO joinReq) throws SQLException {
		memberDao.insert(
					new Member(joinReq.getId(),joinReq.getName(),joinReq.getPassword(),new Date())
						);
	}
	public boolean checkid(String id) throws SQLException {
		String member = null;
		member = memberDao.selectByJoin(id); //이미 그 아이디가 존재하는지 확인
		if(member==null) { //이 아이디가 존재하지 않는 다는 것
			return true;
		}else return false;
	}
	
}
