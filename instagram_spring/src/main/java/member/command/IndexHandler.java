package member.command;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


import com.util.HashMapBinder;

public class IndexHandler extends MultiActionController{
	private static final String FORM_VIEW = "index";
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
		if(req.getSession().getAttribute("state")!=null&&req.getSession().getAttribute("authUser")==null) {	
			//타입을 json으로 바꿔줘야됨
	        res.setContentType("application/json");
	        res.setCharacterEncoding("UTF-8");
			String memberinfo = naverloginapi.profile(req);
			// String형식인 apiResult를 json형태로 바꿈
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(memberinfo);
			JSONObject jsonObj = (JSONObject) obj;
			//데이터 파싱
			//Top레벨 단계 _response 파싱
			JSONObject response_obj = (JSONObject)jsonObj.get("response");
			//response의 nickname값 파싱
			String email = (String)response_obj.get("email");
			//여기서 한글 변환
			String name = new String((String)response_obj.get("name"));
			String namehan = new String(name.getBytes(), "utf-8");
			//아이디 존재하는지 확인 없다면 DB에 추가해주기
			loginservice.naverLogin(email,namehan);
			User user = new User(email,namehan);
			req.getSession().setAttribute("authUser", user); //유저 아이디와 이름 저장
//{"resultcode":"00","message":"success",
//"response":{"id":"XAgjdfO0Y3cVI_LH0P6EzG5901Y6GYJ729DRxntHSuk",
//"nickname":"lucky",
//"profile_image":"https:\/\/phinf.pstatic.net\/contact\/20191116_290\/1573879971051S6e5Y_JPEG\/profileImage.jpg"
//,"email":"goodlucky1215@naver.com","name":"\uc774\uc9c0\ud61c"}}	
			loginResult = processSubmit(req, res);	
		}
		else {
			if(req.getMethod().equalsIgnoreCase("GET")) {
				loginResult = processForm(req, res);
			}
			else if(req.getMethod().equalsIgnoreCase("POST")) {
				loginResult = processSubmit(req, res);			
			}
			else {
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
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