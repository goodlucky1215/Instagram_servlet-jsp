package com.example.demo.instagram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleReadDao {
	private final JdbcTemplate jdbcTemplate;
	public ArticleReadDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Map<String, Object> articleRead(String userid,int articleNum) throws SQLException {
		String sql="select FILENO,FILENAME,MEMBERID,CONTENTTEXT,READ_CNT, case when fileno in (select  jf.FILENO from jspfile jf,JSPHEART jt where  jf.FILENO=(JT.FILENO) and JT.MEMBERID=?) then '1' else '0' end heart from JSPFILE WHERE FILENO=?";
		Map<String, Object> articleread1 = jdbcTemplate.queryForMap(sql,userid,articleNum);
		Map articleread = new HashMap<String, Object>();
		articleread.put("fileNo",articleread1.get("FILENO"));
		articleread.put("fileName",articleread1.get("FILENAME"));
		articleread.put("memberid",articleread1.get("MEMBERID"));
		articleread.put("contentText",articleread1.get("CONTENTTEXT"));
		articleread.put("read_cnt",articleread1.get("READ_CNT"));
		articleread.put("heart",articleread1.get("heart"));
		return articleread;
	}

	public int getDelete(int articleNum) throws SQLException {
		int delete_num = 0;
		String sql1="Delete from JSPFILE WHERE FILENO=?";
		String sql2="Delete from JSPHEART WHERE FILENO=?";
		jdbcTemplate.update(sql1,articleNum);
		jdbcTemplate.update(sql2,articleNum);
		return delete_num;
	}

	public String getDeleteUser(int articleNum) throws SQLException {
		String user = null;
		String sql="select MEMBERID from JSPFILE WHERE FILENO=?";
		user = jdbcTemplate.queryForObject(sql,String.class,articleNum);
		return user;
		
	}
}
