package member.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class IndexHandler extends MultiActionController{
	private static final String FORM_VIEW = "index";
	private LoginService loginservice = new LoginService();
	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String loginResult = null;
		ModelAndView mav = new ModelAndView();
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
			mav.setViewName(loginResult);
		}
		return mav;
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "index";
	}


}