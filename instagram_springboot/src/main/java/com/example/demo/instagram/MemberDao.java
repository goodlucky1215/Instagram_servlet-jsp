package com.example.demo.instagram;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	private final JdbcTemplate jdbcTemplate;
	
	public MemberDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectById(String id) throws SQLException {
		String sql = "select * from jspmember where memberid= ?";
		List<Member> member = jdbcTemplate.query(sql,memberRow(),id);
		return member.get(0);
	}
	private RowMapper<Member> memberRow() {
		return new RowMapper<Member>() {
			
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(
						rs.getString("memberid"),
						rs.getString("name"),
						rs.getString("password")
						);
				return member;
			}
		};
	}
	
	public void insert(Member member) throws SQLException  {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("jspmember").usingGeneratedKeyColumns("NAME");
		// 이것은 이름만 맞으면 문제가 없는 듯 하다. => 받아올 컬럼 적는 것 usingColumns("MEMBERID", "NAME", "PASSWORD", "REGDATE");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("MEMBERID", member.getId());
		param.put("NAME", member.getName());
		param.put("PASSWORD", member.getPassword());
		param.put("REGDATE", new Timestamp(member.getRegDate().getTime()));
		jdbcInsert.execute(new MapSqlParameterSource(param));
		//Integer key = jdbcInsert.Return
		
	}

	public List<Map<String, Object>> selectArticle(String memberId,String text) throws SQLException {
		String findtext="%"+text+"%";
		String sql = "select FILENO,FILEREALNAME,MEMBERID,CONTENTTEXT,READ_CNT, case when fileno in (select  jf.FILENO from jspfile jf,JSPHEART jt where  jf.FILENO=(JT.FILENO) and JT.MEMBERID=?) then '1' else '0' end heart from JSPFILE WHERE CONTENTTEXT LIKE ? order by fileno desc";
		List<Map<String, Object>> articles= jdbcTemplate.query(sql,articlesRow(),memberId,findtext);
		return articles;
	}
	private RowMapper<Map<String,Object>> articlesRow() {
		return new RowMapper<Map<String,Object>>() {
			
			public Map<String,Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object> art = new HashMap<>();
				art.put("fileNo",rs.getInt("FILENO"));
				art.put("fileName",rs.getString("FILEREALNAME"));
				art.put("memberid",rs.getString("MEMBERID"));
				art.put("contentText",rs.getString("CONTENTTEXT"));
				art.put("read_cnt",rs.getString("READ_CNT"));
				art.put("heart",rs.getString("heart"));
				return art;
			}
		};
	}
}
