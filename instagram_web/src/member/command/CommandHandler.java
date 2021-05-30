package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public void process(HttpServletRequest req,HttpServletResponse res)
	throws Exception;
}
