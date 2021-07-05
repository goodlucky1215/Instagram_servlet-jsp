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

import javax.sql.DataSource;

public class MemberDao {
	private Connection con = null;
	private DataSource ds =null;
	public void setDataSource(DataSource ds) {
		this.ds  = ds;
	}
	public Member selectById(Map<String,Object> map) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select memberid,name from jspmember where memberid= ? and password= ?";
		Member member = new Member();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("id").toString());
			pstmt.setString(2, map.get("password").toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member.setId(rs.getString("memberid"));
				member.setName(rs.getString("name"));
			}
		}catch(Exception e){
			
		}	
		return member;
	}
	public void saveAutoIdLogin(Map<String,Object> map,String savetype) throws SQLException {
		con = ds.getConnection();
		String sql = "insert into jspCookie(MEMBERID,autoIdLogin,sessionLimit, savetype) values(?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,map.get("id").toString());
		pstmt.setString(2,map.get("autoIdLogin").toString());
		pstmt.setString(3,map.get("sessionLimit").toString());
		pstmt.setString(4,savetype);
		pstmt.executeUpdate();
	}
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}

	public void insert(Member member) throws SQLException  {
		con = ds.getConnection();
		String sql = "insert into jspmember values(?,?,?,?)";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			System.out.println(member.getId());
			System.out.println(member.getName()); 
			pstmt.setString(1, member.getId());	
			pstmt.setString(2, member.getName());	
			pstmt.setString(3, member.getPassword());	
			pstmt.setTimestamp(4, new Timestamp(member.getRegDate().getTime()));	
			pstmt.executeUpdate();
		} catch (Exception e) {
		}
		
	}

	public List<Map<String, Object>> selectArticle(String memberId,String text) throws SQLException {
		con = ds.getConnection();
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
				articles.add(art);
			}
		} catch(Exception e){
			
		}	
		return articles;
	}
	//네이버 아이디 존재하는지 확인
	public String findNaverId(String email) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select NVL((select MEMBERID from JSPMEMBER where MEMBERID=?),'false') as result from dual";
		String result ="";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getString("result");
			}
		} catch(Exception e){
			
		}	
		return result;
		
	}


}
