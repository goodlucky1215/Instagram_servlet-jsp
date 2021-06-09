package member.command;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		}
		
	}

	public List<Map<String, Object>> selectArticle(Connection con,String memberId,String text) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String findtext="%"+text+"%";
	//고민 많았던걸로 쓰기!
		String sql = "select FILENO,FILEREALNAME,MEMBERID,CONTENTTEXT,READ_CNT, case when fileno in (select  jf.FILENO from jspfile jf,JSPHEART jt where  jf.FILENO=(JT.FILENO) and JT.MEMBERID=?) then '1' else '0' end heart from JSPFILE WHERE CONTENTTEXT LIKE ? order by fileno desc";
		List<Map<String, Object>> articles = new ArrayList<>();
		System.out.println(memberId+findtext);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, findtext);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> art = new HashMap<>();
				art.put("fileNo",rs.getInt("FILENO"));
				art.put("fileName",rs.getString("FILEREALNAME"));
				art.put("memberid",rs.getString("MEMBERID"));
				art.put("contentText",rs.getString("CONTENTTEXT"));
				art.put("read_cnt",rs.getString("READ_CNT"));
				art.put("heart",rs.getString("heart"));
				System.out.println(art.get("fileName"));
				articles.add(art);
			}
		} catch(Exception e){
			
		}	
		finally {
			ConnectionProvider.freeConnection(pstmt,rs);
		}
		return articles;
	}

}
