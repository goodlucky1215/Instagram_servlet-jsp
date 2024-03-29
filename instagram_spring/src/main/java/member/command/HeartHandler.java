package member.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class HeartHandler extends MultiActionController{
	private HeartDao heartdao = null;
	public void setHeartdao(HeartDao heartdao) {
		this.heartdao = heartdao;
	}

	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
		req.setCharacterEncoding("utf-8");
	    res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		int fileno = Integer.parseInt(req.getParameter("click_id"));
		int heart = heartdao.heartAddRe(user.getId(), fileno);
		System.out.println(user.getId());
		if(heart==1) out.print("usable");
		else out.print("able");
	}

}
