package member.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexHandler implements CommandHandler {
	private static final String FORM_VIEW = "index.jsp";
	private LoginService loginservice = new LoginService();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String loginResult = null;
		if(req.getMethod().equalsIgnoreCase("GET")) {
			loginResult = processForm(req, res);
		}
		else if(req.getMethod().equalsIgnoreCase("POST")) {
			loginResult = processSubmit(req, res);			
		}
		else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		if(loginResult!=null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(loginResult);
			dispatcher.forward(req,res);
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "index.jsp";
	}


}