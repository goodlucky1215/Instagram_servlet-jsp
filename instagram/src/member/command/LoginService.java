package member.command;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {
	ConnectionProvider conPro = null;
	private MemberDao memberDao = new MemberDao();
	public User login(String id, String password) {
		conPro = ConnectionProvider.getInstance();
		Connection con = null;
		try{
			con = conPro.getConnection();
			Member member = memberDao.selectById(con, id);
			if(member == null || password.equals(member.getPassword())==false) {
				throw new LoginFailException();
			}
			return new User(member.getId(),member.getName());
		} catch(SQLException e){
			throw new RuntimeException();
		} finally {
			conPro.freeConnection(con);//연결은 무조건 끊어주기
		}
	}
}
