package member.command;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.util.HashMapBinder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
public class LoginHandler extends MultiActionController{
	private static final String FORM_VIEW = "loginForm";
	
	private LoginService loginservice = null;
	public void setLoginService(LoginService loginservice) {
		this.loginservice = loginservice;
	}
	
	private NaverLoginApi naverloginapi = null;
	public void setNaverLoginApi(NaverLoginApi naverloginapi) {
		this.naverloginapi = naverloginapi;
	}
	
	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
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
			mav.setViewName(loginResult);
		}
		return mav;
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
			//res.sendRedirect("mainview.do");
			return"redirect:mainview.do";
		} catch (LoginFailException e) {
			req.setAttribute("message","아이디나 비밀번호가 틀렸습니다.");			
			return  FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str.trim().length() == 0 ? null:str.trim(); //문자열의 첫 시작의 공백을 제거해줌.
	}
	
	
	public String snsprocess(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMapBinder hmb = new HashMapBinder(req);
		Map<String,String> map = naverloginapi.process(); 
	    HttpSession session = req.getSession();
	    session.setAttribute("state", map.get("state"));
		return "redirect:"+map.get("apiURL");
	}

}
