package member.command;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ArticleReadHandler implements CommandHandler{
	private ArticleReadDao articlereaddao = new ArticleReadDao();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		try {
			Map<String, Object> articleread = articlereaddao.articleRead(user.getId(), articleNum);
			System.out.println(articleread.get("contentText"));
			req.setAttribute("articleread",articleread);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/mystudy/instagram/articleRead.jsp");
			dispatcher.forward(req, res);  
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
