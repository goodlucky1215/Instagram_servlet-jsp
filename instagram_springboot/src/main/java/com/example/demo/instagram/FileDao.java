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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
	private final JdbcTemplate jdbcTemplate;
	
	public FileDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public FileVO fileInsert(FileVO filevo) throws SQLException {
		String maxnosql = "select max(fileno)+1 as max_num from ( select fileno  from jspfile union all select 0 from dual )";
		filevo.setFileNo(jdbcTemplate.queryForObject(maxnosql,Integer.class));
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("jspfile");
		Map param = new HashMap<String,Object>();
		param.put("FILENO", filevo.getFileNo());
		param.put("FILENAME", filevo.getFileName());
		param.put("FILEREALNAME", filevo.getFileRealName());
		param.put("MEMBERID", filevo.getMEMBERID());
		param.put("CONTENTTEXT", filevo.getContentText());
		param.put("READ_CNT", 0);
		jdbcInsert.execute(new MapSqlParameterSource(param));
		return filevo;
	}
}
