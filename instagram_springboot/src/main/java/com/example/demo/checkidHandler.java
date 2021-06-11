package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class checkidHandler{
	@Autowired(required=true)
	private JoinService joinService = null;
	@RequestMapping("checkid")
	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		boolean boolId = joinService.checkid(id);
		if(boolId==true) out.print("usable");
		else out.print("able");	
	}

}
