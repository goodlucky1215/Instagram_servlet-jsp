package member.command;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ControllerUsingURI extends HttpServlet{
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	public void init() throws ServletException{
		String confingFile = getInitParameter("configFile");
		System.out.println(confingFile);
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(confingFile);
		System.out.println(configFilePath);
		
		try(FileReader fis = new FileReader(configFilePath)){
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		System.out.println(prop);
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
				System.out.println(command);
				commandHandlerMap.put(command,handlerInstance);
				System.out.println(commandHandlerMap.get(command));
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException{
		process(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		process(request, response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		String command = request.getRequestURI();
		System.out.println(command);// /mystudy/instagram/join.do
		System.out.println(request.getContextPath());// 없을거임 왜냐면 server.xml의 경로가 /로 되어있기때문
		if(command.indexOf(request.getContextPath())==0) {
			System.out.println(request.getContextPath().length());
			command = command.substring(request.getContextPath().length());
			System.out.println(command);// /mystudy/instagram/join.do
		}
		CommandHandler handler = commandHandlerMap.get(command);
		if(handler == null) {
			System.out.println("값 존재 안함.");
			handler = new NullHandler();
		}
		try {
			System.out.println(handler); // member.command.JoinHandler2@ec2cc4
			handler.process(request, response);
		} catch (Throwable e) {
			throw new ServletException(e);
		}		
	}
}
