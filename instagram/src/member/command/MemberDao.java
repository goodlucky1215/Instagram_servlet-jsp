package member.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class MemberDao {

	public Member selectById(Connection con, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from jspmember where memberid= ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Member member = null;
			if(rs.next()) {
				member = new Member(
						rs.getString("memberid"),
						rs.getString("name"),
						rs.getString("password"),
						toDate(rs.getTimestamp("regdate"))
						);
			}
			return member;
		} finally {
			ConnectionProvider.freeConnection(pstmt,rs);
		}
	}

	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}

	public void insert(Connection con, Member member) throws SQLException  {
		System.out.println("저장1");
		String sql = "insert into jspmember values(?,?,?,?)";
		System.out.println("저장1");
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			System.out.println("저장1");
			pstmt.setString(1, member.getId());	
			System.out.println("저장1");
			pstmt.setString(2, member.getName());	
			System.out.println("저장1");
			pstmt.setString(3, member.getPassword());	
			System.out.println(member.getRegDate().getTime());
			pstmt.setTimestamp(4, new Timestamp(member.getRegDate().getTime()));	
			System.out.println("저장1");
			pstmt.executeUpdate();
			System.out.println(pstmt);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void checkid(Connection con,String id) {
		// TODO Auto-generated method stub
		
	}

}
