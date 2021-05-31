package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class checkidHandler implements CommandHandler{
	private JoinService joinService = new JoinService();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		boolean boolId = joinService.checkid(id);
		if(boolId==true) out.print("usable");
		else out.print("able");
	}

}
