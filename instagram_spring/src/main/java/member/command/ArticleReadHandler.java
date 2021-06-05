package member.command;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.util.HashMapBinder;

public class ArticleReadHandler extends MultiActionController{
	private ArticleReadDao articlereaddao = null;
	public void setArticlereaddao(ArticleReadDao articlereaddao) {
		this.articlereaddao = articlereaddao;
	}

	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		try {
			Map<String, Object> articleread = articlereaddao.articleRead(user.getId(), articleNum);
			System.out.println(articleread.get("contentText"));
			req.setAttribute("articleread",articleread);
			mav.setViewName("articleRead");  
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
}
