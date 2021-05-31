package member.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class JoinService {
	ConnectionProvider conPro = null;
	private MemberDao memberDao = new MemberDao();
	public void join(MemberVO joinReq) {
		conPro = ConnectionProvider.getInstance();
		Connection con = null;
		try {
			System.out.println("1");
			con = conPro.getConnection();
			System.out.println("2");
			con.setAutoCommit(false);//자동커밋을 해제한다
			System.out.println("3");
			memberDao.insert(
					con,
					new Member(joinReq.getId(),joinReq.getName(),joinReq.getPassword(),new Date())
							);
			con.commit(); //저장해준다.
		} catch (SQLException e) {
			//예상치 못한 에러가 발생한다면,
			conPro.rollback(con);//트랜잭션 롤백하고
			throw new RuntimeException(e); //에러처리
		} finally {
			conPro.freeConnection(con);//연결은 무조건 끊어주기
		}
	}
	public boolean checkid(String id) {
		conPro = ConnectionProvider.getInstance();
		Connection con= null;
		Member member = null;
		try {
			con = conPro.getConnection();
			member = memberDao.selectById(con,id); //이미 그 아이디가 존재하는지 확인
		} catch (SQLException e) {
			conPro.rollback(con);
			throw new RuntimeException(e);
		} finally {
			conPro.freeConnection(con);
		}
		if(member==null) { //이 아이디가 존재하지 않는 다는 것
			return true;
		}else return false;
	}
	
}
