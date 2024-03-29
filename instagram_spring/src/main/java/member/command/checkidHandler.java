package member.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.util.HashMapBinder;

public class checkidHandler extends MultiActionController {
	private JoinService joinService = null;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		boolean boolId = joinService.checkid(id);
		System.out.println(boolId);
		if(boolId==true) out.print("usable");
		else out.print("able");	
	}

}
