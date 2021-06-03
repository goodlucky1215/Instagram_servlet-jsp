package member.command;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;

public class MainVeiwHandler extends MultiActionController{
	private static final String FORM_VIEW = "loginForm";
	private LoginService loginservice = new LoginService();
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String result = null;
		if(req.getMethod().equalsIgnoreCase("GET")) {
			result = processForm(req, res);
		}
		else if(req.getMethod().equalsIgnoreCase("POST")) {
			result = processSubmit(req, res);			
		}
		else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		if(result!=null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(result);
			dispatcher.forward(req,res);
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		List<Map<String,Object>> articles = loginservice.articleList(user.getId(),"_");

        //req.setAttribute("articles", articles);
       
        //타입을 json으로 바꿔줘야됨
        //res.setContentType("application/json");
        //res.setCharacterEncoding("UTF-8");
       
        //json 형태의 string으로 바꿔준다.
        String gson = new Gson().toJson(articles);
		//둘 다 입력했다면 이제 찾기 시작
		try {
            //ajax로 리턴해주는 부분
            res.getWriter().write(gson);
			return null;
		} catch (LoginFailException e) {	
			return  FORM_VIEW;
		}
	}

}
