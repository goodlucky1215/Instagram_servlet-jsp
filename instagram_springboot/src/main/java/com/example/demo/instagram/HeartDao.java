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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class HeartDao {
	private final JdbcTemplate jdbcTemplate;
	public HeartDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int heartAddRe(String userid,int articleNum) throws SQLException {
		String heartsql1="select count(*) as heartsu from jspheart where memberid=? and fileno=?";
		int heartsu=jdbcTemplate.queryForObject(heartsql1,Integer.class,userid,articleNum);
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
		jdbcTemplate.update(sql1, userid,articleNum);
		jdbcTemplate.update(sql2, articleNum);
		return heartsu;
	}

}
