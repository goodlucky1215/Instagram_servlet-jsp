<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
  <link rel="stylesheet" type="text/css" href="/css/loginForm.css">
</head>

<body>
    <form action="/mystudy/instagram/loginForm" method="post" id="main__shape">
      <div id="loginform__shape">
        <p class="loginform__title__logo">INSTAGRAM</p>
        <p>
          아이디<br /><input type="text" name="id" value="${param.id}" class="loginform__input">
        </p>
        <p>
          비밀번호<br /><input type="password" name="password" class="loginform__input">
        </p>
        <div class="error">${message}</div>
        <input type="submit" value="로그인" class="loginbtn">
      </div>
    </form>
    <form action="join" method="get" id="join__shape">
      <div>
        아직 계정이 없으신가요?
        <input type="submit" value="회원가입"  class="joinbtn">
      </div>
    </form>
</body>

</html>