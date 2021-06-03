package member.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class LogoutHandler extends MultiActionController{

	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession session = req.getSession(false);

		if(session!=null) {
			session.invalidate();
		}
		res.sendRedirect("login.do");
	}

}
