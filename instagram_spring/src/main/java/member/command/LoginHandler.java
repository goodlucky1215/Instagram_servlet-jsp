package member.command;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
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
			Map<String,Object> map = new HashMap<>();
			map.put("id",id);
			map.put("password",password);
			Cookie autoIdLogin = null;
			if(req.getParameter("isSavedIdChecked")!=null || req.getParameter("isAutoLoginChecked")!=null) {
				//쿠키 생성
				autoIdLogin = new Cookie("autoIdLogin",req.getSession().getId());
				autoIdLogin.setPath("/"); //현재 로그인 페이지 경로에 저장할 거란 거임
				autoIdLogin.setMaxAge(60*60*24*7); //그 쿠키 날짜를 설정해둔다.
				String sessionLimit = (new Date(System.currentTimeMillis()+(1000*60*60*24*7))).toString();//세션 만료날짜설정
				map.put("autoIdLogin",req.getSession().getId());
				map.put("sessionLimit",sessionLimit);
				//ID저장과 자동로그인 => 체크 박스 체크한채로 전송시키면 on이 찍히고, 아니면 null이 찍힌다.
				map.put("isSavedIdChecked",req.getParameter("isSavedIdChecked"));
				map.put("isAutoLoginChecked",req.getParameter("isAutoLoginChecked"));
			}
			User user = loginservice.login(map);
			if(autoIdLogin!=null)res.addCookie(autoIdLogin); //ID저장과 자동로그인  둘 중 하나가 존재한다면 쿠키값으로 저장시켜준다
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
