package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HeartHandler implements CommandHandler{
	private HeartDao heartdao = new HeartDao();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
