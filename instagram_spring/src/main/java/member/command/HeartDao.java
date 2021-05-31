package member.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeartDao {
	public int heartAddRe(String userid,int articleNum) {
		ConnectionProvider conpro = ConnectionProvider.getInstance();
		Connection con = conpro.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int heartsu= 0;
		String heartsql1="select count(*) as heartsu from jspheart where memberid=? and fileno=?";
		try {
			//사진 업로드 되는 순서
			pstmt = con.prepareStatement(heartsql1);
			pstmt.setString(1, userid);
			pstmt.setInt(2, articleNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				heartsu = rs.getInt("heartsu");
			}
			String sql1 = null;
			String sql2 = null;
			if(heartsu==0) {
				sql1= "Insert INTO jspheart(memberid,fileno) VALUES(?,?)";
				sql2= "UPDATE jspfile SET read_cnt=read_cnt+1 where fileno=?";
			}
			else if(heartsu==1) {
				sql1= "DELETE FROM jspheart WHERE memberid=? AND fileno=?";
				sql2= "UPDATE jspfile SET read_cnt=read_cnt-1 where fileno=?";
			}
			//좋아요 넣거나  빼기
			pstmt = con.prepareStatement(sql1);
			System.out.println(1);
			pstmt.setString(1, userid);
			System.out.println(2);
			pstmt.setInt(2, articleNum);
			System.out.println(7);
			pstmt.executeUpdate();
			//좋아요 횟수 넣거나  빼기
			pstmt = con.prepareStatement(sql2);
			System.out.println(1);
			pstmt.setInt(1, articleNum);
			System.out.println(7);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			conpro.freeConnection(con, pstmt, rs);
		}
		return heartsu;
	}

}
