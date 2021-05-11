package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinHandler implements CommandHandler{

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("hello","나아나ㅏ나" );
	}

}
