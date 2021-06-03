package member.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class checkidHandler extends MultiActionController {
	private JoinService joinService = new JoinService();
	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ModelAndView mav = new ModelAndView();
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		boolean boolId = joinService.checkid(id);
		if(boolId==true) out.print("usable");
		else out.print("able");	
	}

}
