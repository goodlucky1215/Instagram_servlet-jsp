package member.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoginService {
	private MemberDao memberDao = null;
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
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
	public void naverLogin(String email, String namehan) throws SQLException {
		//없는 아이라면 추가해준다.
		if("false".equals(memberDao.findNaverId(email))) {
		System.out.println("dddd");
			Member member = new Member(email,namehan,null,new Date());
			memberDao.insert(member);
		};
		
	}
}
