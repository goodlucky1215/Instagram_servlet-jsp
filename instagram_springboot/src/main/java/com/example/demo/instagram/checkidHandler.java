package com.example.demo.instagram;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class checkidHandler{
	@Autowired(required=true)
	private JoinService joinService = null;
	@RequestMapping("checkid")
	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
		HashMapBinder hmb = new HashMapBinder(req);
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		boolean boolId = joinService.checkid(id);
		if(boolId==true) out.print("usable");
		else out.print("able");	
	}

}
