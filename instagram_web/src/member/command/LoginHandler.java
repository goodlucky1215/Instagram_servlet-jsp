package member.command;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler implements CommandHandler {
	private static final String FORM_VIEW = "loginForm.jsp";
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
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));
		//아이디나 비밀번호 입력 확인
		if(id == null || password == null) {
			req.setAttribute("message","아이디나 비밀번호를 입력해주세요!");
			return FORM_VIEW;
		}
		//둘 다 입력했다면 이제 찾기 시작
		try {
			User user = loginservice.login(id, password);
			req.getSession().setAttribute("authUser", user); //유저 아이디와 이름 저장
			res.sendRedirect("mainview.do");
			return null;
		} catch (LoginFailException e) {
			req.setAttribute("message","아이디나 비밀번호가 틀렸습니다.");			
			return  FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str.trim().length() == 0 ? null:str.trim(); //문자열의 첫 시작의 공백을 제거해줌.
	}

}
