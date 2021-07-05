package member.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.util.HashMapBinder;

public class LogoutHandler extends MultiActionController{

	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HashMapBinder hmb = new HashMapBinder(req);
		HttpSession session = req.getSession(false);

		if(session!=null) {
			session.invalidate();
		}
		res.sendRedirect("login.do");
	}

}
