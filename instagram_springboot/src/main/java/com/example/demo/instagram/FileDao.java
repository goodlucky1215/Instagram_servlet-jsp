package com.example.demo.instagram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
	private Connection con = null;
	@Autowired
	private DataSource ds =null;
	public FileVO fileInsert(FileVO filevo) throws SQLException {
		con = ds.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String maxnosql = "select max(fileno)+1 as max_num from ( select fileno  from jspfile union all select 0 from dual )";
		String insertsql = "insert into jspfile ( fileNo , fileName , fileRealName , MEMBERID , contentText , read_cnt ) values (?,?,?,?,?,0)";
		try {
			//사진 업로드 되는 순서
			pstmt = con.prepareStatement(maxnosql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				filevo.setFileNo(rs.getInt("max_num"));
			}
			System.out.println(filevo.getFileNo());
			//사진 저장하기
			pstmt = con.prepareStatement(insertsql);
			System.out.println(1);
			pstmt.setInt(1, filevo.getFileNo());
			System.out.println(2);
			pstmt.setString(2, filevo.getFileName());
			pstmt.setString(3, filevo.getFileRealName());
			System.out.println(3);
			pstmt.setString(4, filevo.getMEMBERID());
			pstmt.setString(5, filevo.getContentText());
			System.out.println(7);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return filevo;
	}
}
