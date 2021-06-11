package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleReadDao {
	private Connection con = null;
	@Autowired
	private DataSource ds =null;
	public Map<String, Object> articleRead(String userid,int articleNum) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> articleread = new HashMap<>();
		String sql="select FILENO,FILENAME,MEMBERID,CONTENTTEXT,READ_CNT, case when fileno in (select  jf.FILENO from jspfile jf,JSPHEART jt where  jf.FILENO=(JT.FILENO) and JT.MEMBERID=?) then '1' else '0' end heart from JSPFILE WHERE FILENO=?";
		try {
			pstmt = con.prepareStatement(sql);
			System.out.println(userid);
			pstmt.setString(1, userid);
			System.out.println(userid);
			pstmt.setInt(2, articleNum);
			System.out.println(articleNum);
			rs = pstmt.executeQuery();
			System.out.println(articleNum);
			while(rs.next()) {
				System.out.println(articleNum);
				articleread.put("fileNo",rs.getInt("FILENO"));
				articleread.put("fileName",rs.getString("FILENAME"));
				articleread.put("memberid",rs.getString("MEMBERID"));
				articleread.put("contentText",rs.getString("CONTENTTEXT"));
				articleread.put("read_cnt",rs.getString("READ_CNT"));
				articleread.put("heart",rs.getString("heart"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return articleread;
	}

	public int getDelete(int articleNum) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int delete_num = 0;
		String sql1="Delete from JSPFILE WHERE FILENO=?";
		String sql2="Delete from JSPHEART WHERE FILENO=?";
		try {
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, articleNum);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, articleNum);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return delete_num;
	}

	public String getDeleteUser(int articleNum) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String user = null;
		String sql="select MEMBERID from JSPFILE WHERE FILENO=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, articleNum);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				user=rs.getString("MEMBERID");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user;
		
	}
}
