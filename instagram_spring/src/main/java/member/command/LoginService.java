package member.command;

import java.sql.SQLException;
import java.util.ArrayList;
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
}
