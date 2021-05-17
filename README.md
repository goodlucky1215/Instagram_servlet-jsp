# [Instagram_servlet-jsp_인스타그램 만들기]
## 이 프로젝트의 목표 
> 1. jsp와 servlet의 전체적으로 돌아가는 과정을 이해, web.xml 사용법 + 세션 맛보기
> 2. ajax 사용해보기
> 3. 오라클에서 데이터를 받아와서 json형태로 변환 후, 사용해보기
> 4. 사진(cos.jar 사용)이랑 글 올리기

------------

### [web.xml 파일 안] 
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
	        //즉, 톰캣을 구동하는 시점에서 서블릿 객체를 생성하고, init()메서드를 실행한다.
		//그리고 내가 웹을 키면 커넥션 풀을 초기화하므로, JSP나 서블릿 코드에서 커넥션 풀을 사용.
		<load-on-startup>1</load-on-startup>
     // 이 1은 순서를 의미 - 여러 servlet이 존재할때 로딩 순서를 이 숫자로 결정할 수 있다.

  </servlet>
  
  <servlet-mapping> //해당 서블릿이 어떤 URL을 처리할지 대한 매핑 정보를 등록(url의 이름을 servlet이 보는 이름으로 바꿔서 넣어준것)하는 것이다.
  	<servlet-name>ControllerUsingURI</servlet-name> //매핑할 서블릿의 이름을 지정
  	<url-pattern>*.do</url-pattern> 
    //매핑할 URL패턴을 지정한다. *.do는 .do가 들어간 URl을 전부 매핑
  </servlet-mapping>

 <filter> <!-- 필터는 클라와 내가 들어가려는 서블릿 그 사이에 존재해서 한 번 필터링(예를 들어 로그인 하고 들어왔는지 아닌지 체크한다던가)을 해주는 것임. -->
  	<filter-name>LoginCheckFilter</filter-name><!-- 내가 사용하려는 필터링 이름 -->
  	<filter-class>member.command.LoginCheckFilter</filter-class> <!-- 사용하는 필터링 클래스 위치 -->
  </filter>
  <filter-mapping>
  	<filter-name>LoginCheckFilter</filter-name> <!-- 만약 같은 url-pattern을 여러게 필터 처리하면 순서대로 필터링을 거치고 화면을 받아온다 -->
  	<url-pattern>/mystudy/instagram/index.jsp</url-pattern> <!-- 내가 mainview를 킬때마다 로그인 되어 있는지 확인 -->
  	<url-pattern>/mystudy/instagram/upload.do</url-pattern> <!-- 내가 새로운 게시글을 올릴 때마다 로그인 되어 있는지 확인 -->
  	<dispatcher>REQUEST</dispatcher> <!-- 이게 디폴트로 클라가 요청할때마다 필터를 낀 후에 서블릿이 동작하도록 해준다. -->
  </filter-mapping>
//properties안 에는 
//mystudy/gasipan/join.do=member.command.JoinHandler2 이렇게 들어가 있다. 따라서,
//결과적으로 ControllerUsingURI는 /mystudy/Instagram/join.do 이 들어가있는 URL을 처리하게 된다!
//즉, http://localhost:9000/mystudy/Instagram/join.do 얘를 처리하는 것임.
```

------------

### [member.command.ControllerUsingURI.java 안] - commandController 모든 요청은 여기서 한 번에 받지만, 처리는 각자 보내진 서블릿 안에서 처리함.
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
	        //server.xml에 path=""으로 되어있다면 request.getContextPath()=""이 된다. - 항상 경로는 무조건 확인할 것
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

------------

### [LoginHandler.java 파일임.]-로그인으로 알아보는 세션
```java
try {
	//데이터베이스에서 로그인 아이디와 비밀번호임을 확인하고 만약 맞다고 가정한다면.
			User user = loginservice.login(id, password);
 	//getSession()메서드는 서버에 생성된 세션이 있다면 세션을 반환하고, 없다면 세 세션을 생성하여 반환 - 디폴트 값이 true임.
	//getSession().setAttribute("authUser", user) 세션에다가 값을 저장한다는 것이다. -즉 서버에 저장(캐시) - redirect를 해도 날라가지 않겠다!!
			req.getSession().setAttribute("authUser", user);
			res.sendRedirect("/mystudy/Instagram/index.jsp");
			return null;
		} catch (LoginFailException e) {
			req.setAttribute("message","아이디나 비밀번호가 틀렸습니다.");			
			return  FORM_VIEW;
		}
```

------------

### [LogoutHandler.java 파일임.]-세션 반납.

```java
@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);
	//req.getSession(false);로 입력시, session이 존재하면 값을 반환하고, 존재하지 않으면 굳이 새로운 세션을 생성하지 않겠다는 것임
	//역으로 req.getSession(true); == req.getSession();로 입력시, session이 존재하면 값을 반환하고, 존재하지 않으면 새로운 세션을 생성하겠다는 것임. 
		if(session!=null) {
			session.invalidate(); //세션을 반환한다.
		}
		res.sendRedirect("/mystudy/Instagram/login.jsp"); //로그인 페이지로 돌아간다.
	}
```

------------

### [idcheck.js] 회원가입 시 로그인 아이디 중복 확인(비동기-Jquery-Ajax)
+ 비동기란 프로그램의 흐름과 무관하게 이벤트를 발생시키는 것, ajax는 그래서 화면의 일부만 로드하는 것임
```javascript
"use strict"
function idcheck(){ //화원가입 id를 입력 중복확인을 위한 이 loop가 돌아감.
  const id = $('input[name=id]').val();
  if(id.length<=0){
	$('#message').text('') //아무것도 입력하지 않았다면 text를 비워준다.
	return;
  }	
  $.ajax({
    type: 'post', //보낼 때 방식
    url: 'checkid.do', //서버에서 받을 때 인식할 url
    dataType:'text', //서버로부터 내가 받는 데이터 타입
    data:{id:id}, //내가 입력한 id라는 데이터를 id라는 이름으로 서버에게 넘겨줌. -req안에 들어감.
    success: function(data, textStatus){ //이 data안에 내가 서버로부터 받는 값이 들어가 있음!!
      if(data === 'usable'){
        $('#message').text('사용할 수 있는 ID입니다.')
        $('#signBtn').prop('disabled', false)
      } else {
        $('#message').text('이미 사용 중인 아이디입니다.')
        $('#signBtn').prop('disabled', true)
      }
    },
    error:function(data,textStatus){
      console.log('error');
    }

  })
}
```
### [checkidHandler-Ajax] 회원가입 시 로그인 아이디 중복 확인(비동기-Jquery-Ajax)
```java
public class checkidHandler implements CommandHandler{
	private JoinService joinService = new JoinService();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id"); //ajax에서 넣어줬던 값을 가져옴.
		boolean boolId = joinService.checkid(id); //존재하는 아이디인지 DB에서 체크 후 가지고 나옴
		if(boolId==true) out.print("usable"); //아이디가 존재 안한다는 의미의 "usable"로 success: function(data)에서 data안에 들어가고,
		else out.print("able"); //아이디가 존재한다의 "able"로 success: function(data)에서 data안에 들어감.
	}

}
```

