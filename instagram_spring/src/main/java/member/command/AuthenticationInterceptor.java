package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//로그인 처리 인터셉터
public class AuthenticationInterceptor implements HandlerInterceptor {
	private LoginService loginservice = null;
	public void setLoginService(LoginService loginservice) {
		this.loginservice = loginservice;
	}
	
	//preHandle()은 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)throws Exception {
		//쿠키에서 autoIdLogin에서 존재한다면 자동로그인이나 아이디 저장
		Cookie[] cookies = null;
		cookies = req.getCookies();
	    Map<String, Object> map = new HashMap<>();
	    if(cookies!=null) {
		for(Cookie cookie:cookies) {
			if("autoIdLogin".equals(cookie.getName())) {
				//로그아웃 상황 일때는 그 쿠키값이 존재한다면 그것을 삭제
				if(req.getParameter("logout")!=null) {
					User user = (User) req.getSession().getAttribute("authUser");
					loginservice.autoCookiedelete(user.getId());
					Cookie cookie1 = new Cookie("autoIdLogin",null);
					cookie1.setMaxAge(0);
					res.addCookie(cookie1);
					return true;
				}
				//로그인 상황일때는 그 멤버의 정보를 저장해옴				
				else {
					map = loginservice.autoCookieConfirm(cookie.getValue());
				}
				break;
			}
		}}
	    //자동로그인이 아님+로그아웃 할 것이라면 그냥 로그아웃해주기
	    if(req.getParameter("logout")!=null) {
			return true;
		}
		//만약 유효한 쿠키가 존재한다면
		if(map.containsKey("SAVETYPE")) {
			System.out.println("여기오니");
			if("login".equals(map.get("SAVETYPE").toString())) {
				System.out.println("여기오니1");
				User user = new User(map.get("MEMBERID").toString(),map.get("name").toString());
				req.getSession().setAttribute("authUser", user); //세션에 정보를 담아서
				res.sendRedirect("mainview.do"); //전송
			}
			else if("id".equals(map.get("SAVETYPE").toString())) {
				System.out.println("여기오니2");
				req.setAttribute("email", map.get("MEMBERID").toString());
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle	(HttpServletRequest req, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) 
			throws Exception {
		// TODO Auto-generated method stub
	}
}
