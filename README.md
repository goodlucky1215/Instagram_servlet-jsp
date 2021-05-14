# Instagram_servlet-jsp
##[web.xml 파일 안] 
```xml
<servlet> // <-- 이렇게 하면 서블릿 클래스를 등록하는 것임
  	<servlet-name>ControllerUsingURI</servlet-name> //<--해당 서블릿을 참조할 때 사용할 이름
  	<servlet-class>member.command.ControllerUsingURI</servlet-class>//<-- 서블릿으로 사용할 클래스의 완전한 이름을 입력
  	//그러니깐 member.command.ControllerUsingURI 클래스를 사용할 때, 
    //ControllerUsingURI라는 이름으로 사용한다는 의미다
     

		//이거에 대한 설명은 ControllerUsingURI.java안에 있다.
		<init-param>
  		<param-name>configFile</param-name>
  		<param-value>
  			/WEB-INF/classes/commandHandlerURI.properties
  		</param-value>
  	</init-param>

		//보통 초기화 작업은 시간이 오래 걸린다. 처음 서블릿을 사용하는 시점보다 웹 컨테이너를
		//처음 구동하는 시점에 초기화를 진행하는 것이 좋다. 
    //--->이를 위한 작업이 <load-on-startup>1</load-on-startup>
  	//508p - 17.4 그림확인
	  //즉, 톰캣을 구동하는 시점에서 서블릿 객체를 생성하고, init()메서드를 실행한다.
		//그리고 내가 웹을 키면 커넥션 풀을 초기화하므로, JSP나 서블릿 코드에서 커넥션 풀을 사용.
		<load-on-startup>1</load-on-startup>
     // 이 1은 순서를 의미 - 여러 servlet이 존재할때 로딩 순서를 이 숫자로 결정할 수 있다.

  </servlet>
  
  <servlet-mapping> //해당 서블릿이 어떤 URL을 처리할지 대한 매핑 정보를 등록하는 것이다.
  	<servlet-name>ControllerUsingURI</servlet-name> //매핑할 서블릿의 이름을 지정
  	<url-pattern>*.do</url-pattern> 
    //매핑할 URL패턴을 지정한다. *.do는 .do가 들어간 URl을 전부 매핑
  </servlet-mapping>
//properties안 에는 
//mystudy/gasipan/join.do=member.command.JoinHandler2 이렇게 들어가 있다. 따라서,
//결과적으로 ControllerUsingURI는 /mystudy/Instagram/join.do 이 들어가있는 URL을 처리하게 된다!
//즉, http://localhost:9000/mystudy/Instagram/join.do 얘를 처리하는 것임.
```

##**[member.command.ControllerUsingURI.java 안] - 즉 서블릿에서 사용된 클래스**
```java
public void init() throws ServletException{ //init()은 서블릿을 초기화 할 때 사용
	  String confingFile = getInitParameter("configFile");
    //<init-param>안에 있는 <param-name>이 configFile인
  	//<param-value>값을 가져옴.
		//즉 confingFile = "/WEB-INF/classes/commandHandlerURI.properties";이 됨.
		Properties prop = new Properties(); //properties를 넣을 통(즉 경로를 넣을 통)
		String configFilePath = getServletContext().getRealPath(confingFile);
    // /WEB-INF/classes/commandHandlerURI.properties얘의 진짜 경로를 가져옴.
		System.out.println(configFilePath);
		
		try(FileReader fis = new FileReader(configFilePath)){//commandHandlerURI.properties이 파일을 읽음.
			prop.load(fis);//그리고 prop안에 저장
		} catch (IOException e) {
			throw new ServletException(e);
		}
		System.out.println(prop);//prop = {/mystudy/gasipan/join.do=member.command.JoinHandler2} 임.
		Iterator keyIter = prop.keySet().iterator(); 
		while(keyIter.hasNext()) {
			String command = (String) keyIter.next(); // command = "/mystudy/Instagram/join.do";
			String handlerClassName = prop.getProperty(command);
			//prop.getProperty(command)를 하면 properties를 가지고 있는 prop가 /mystudy/Instagram/join.do의
			//값 즉 member.command.JoinHandler2를 전달시켜줌.
			// handlerClassName="member.command.JoinHandler2"가 됨.
			try {
				Class<?> handlerClass = Class.forName(handlerClassName); //handlerClassName로 해당 클래스 객체를 구한다는 것임.
				//즉, handlerClass라고 선언하고 JoinHandler2.java를 넣는다. 그래서 Class<?>임.
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance(); //여기서 해당 클래스 객체를 생성한다.
				System.out.println(command);
				commandHandlerMap.put(command,handlerInstance); // == (/mystudy/Instagram/join.do,member.command.JoinHandler2@37e94901)
				System.out.println(commandHandlerMap.get(command)); // member.command.JoinHandler2@37e94901
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
}
//init()은 <load-on-startup>1</load-on-startup>로인해서 톰캣 서버를 킨 순간부터 켜짐

//이건 이제 내가 홈페이지를 전송하면 발생
public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException{
		process(request, response);
	}
	
//이건 이제 내가 홈페이지를 전송하면 발생
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		process(request, response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException{
		String command = request.getRequestURI(); // **join.do로 전송을 받음
      //request.getRequestURI() 함수 = 프로젝트 + 파일경로까지 가져옵니다.
      //ex)http://localhost:8080/project/list.jsp ---> /project/list.jsp를 가져온다.
		//왜냐면 joinForm.jsp안에  <form **action="join.do"** method="post"> 로 되있기 때문
		System.out.println(command);// /mystudy/Instagram/join.do
		if(command.indexOf(request.getContextPath())==0) {
			//시작점 위치를 시작으로 경로가 같다면 (즉 시점의 index가 그러면 0일테니까, 안으로 들어옴.)
	    //request.getContextPath() 함수는 = **프로젝트 Path만**
      //ex)http://localhost:8080/project/list.jsp  ---> /project 이 부분을 가져온다.
			//contextpath는 server.xml 경로와 관련이 있으므로
	    //server.xml에 path=""으로 되어있다면 request.getContextPath()=""이 된다.
			command = command.substring(request.getContextPath().length())

			System.out.println(command);// /mystudy/Instagram/join.do
		}
    //commandHandlerMap.get(command) --> /mystudy/Instagram/join.do의 클래스를 가져온다
		//즉 handler = member.command.JoinHandler2@ec2cc4 를 가져옴.
		// 여기서 클래스가 JoinHandler인데 CommandHandler를 쑬 수 있는 이유는
    //JoinHandler가 CommadHadler를 상속받고 있기 때문이다.
		CommandHandler handler = commandHandlerMap.get(command);
		if(handler == null) {
			handler = new NullHandler();
		}
		String viewPage = null;
		try {
			viewPage = handler.process(request, response); // 이것으로 이제 이벤트 컨트롤러와 함께 dao등등이 발생
		} catch (Throwable e) {
			throw new ServletException(e);
		}
		if(viewPage != null) {
			//view에 전해줄 결과물이 여기로 받아서 전달 시켜주는 것임.
			//RequestDispatcher는 클라이언트로부터 최초에 들어온 요청을 
			//JSP/Servlet 내에서 원하는 자원으로 요청을 넘기는(보내는) 역할을 수행하거나,
			//특정 자원에 처리를 요청하고 처리 결과를 얻어오는 기능을 수행하는 클래스
			// 더 쉽게 말하면
      // /a.jsp 로 들어온 요청을 /a.jsp 내에서 RequestDispatcher를 사용하여 b.jsp로 요청
			//또는 a.jsp에서 b.jsp로 처리를 요청하고 b.jsp에서 처리한 결과 내용을 a.jsp의 결과에 포함
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
      //RequestDispatcher의 종류는 .forward와 include가 있다.
//forward는 아예 새로운 페이지 내용을 전달(그러나 url은 원래 사용하고 있던 join.do를 사용함 즉 url을 바뀌지 않음.
//include는 현재 켜져있는 페이지에서 살을 덧붙여서 전달 시켜주는 것임.
			dispatcher.forward(request, response);
		}
	}
```

##[로그인으로 알아보는 세션]
###LoginHandler.java 파일임.
```java
try {
	//데이터베이스에서 로그인 아이디와 비밀번호임을 확인하고 만약 맞아다고 가정한다면.
			User user = loginservice.login(id, password);
  //getSession()메서드는 서버에 생성된 세션이 있다면 세션을 반환하고, 없다면 세 세션을 생성하여 반환
  //getSession().setAttribute("authUser", user) 세션에다가 값을 저장한다는 것이다.
			req.getSession().setAttribute("authUser", user);
			res.sendRedirect("/mystudy/Instagram/index.jsp");
			return null;
		} catch (LoginFailException e) {
			req.setAttribute("message","아이디나 비밀번호가 틀렸습니다.");			
			return  FORM_VIEW;
		}
```
###LogoutHandler.java 파일임.
```java
@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);
//req.getSession(false); 은 session이 존재하면 값을 반환하고, 존재하지 않으면 굳이 새로운 세션을
//생성하지 않겠다는 것임
//역으로 req.getSession(true);는 session이 존재하면 값을 반환하고, 존재하지 않으면 새로운 세션을
//생성하겠다는 것임. 
		if(session!=null) {
			session.invalidate(); //세션을 반환한다.
		}
		res.sendRedirect("/mystudy/Instagram/login.jsp"); //로그인 페이지로 돌아간다.
	}
```
