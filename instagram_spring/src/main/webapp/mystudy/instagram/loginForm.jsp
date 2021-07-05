<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
  <link rel="stylesheet" href="loginForm.css?v=<%=System.currentTimeMillis()%>">
</head>

<body>
	<div>
	    <form action="login.do" method="post" id="main__shape">
	      <div id="loginform__shape">
	        <p class="loginform__title__logo">INSTAGRAM</p>
	        <p>
	          아이디<br /><input type="text" name="id" value="${param.id}" class="loginform__input">
	        </p>
	        <p>
	          비밀번호<br /><input type="password" name="password" class="loginform__input">
	        </p>
	        <div class="error">${message}</div>
	        <div>
				<div>
					<input name="isSavedIdChecked" type="checkbox" id="saveId">
					<label for="saveId">ID 저장</label>
				</div>
				<div>
					<input name="isAutoLoginChecked" type="checkbox" id="autoLogin"> 
					<label for="autoLogin">자동로그인</label>
				</div>
			</div>
	        <input type="submit" value="로그인" class="loginbtn">
	      </div>
	      <div id="naver__shape">
		    <a href="loginNaver.do">
		   		<img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/>
		    </a>
	      </div>
	    </form>
	</div>
	    <form action="join.do" method="get" id="join__shape">
      <div>
        아직 계정이 없으신가요?
        <input type="submit" value="회원가입"  class="joinbtn">
      </div>
    </form>
</body>

</html>