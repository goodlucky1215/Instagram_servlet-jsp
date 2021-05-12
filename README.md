# Instagram_servlet-jsp
```xml
[web.xml 파일 안]
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
