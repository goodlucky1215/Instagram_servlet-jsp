<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 성공</title>
<link rel="stylesheet" href="/css/joinForm.css">
</head>
<body>
  <div id="main__shape__success">
    <div  class="main__shape__success__text">🎉반갑습니다!🎉</div>
    <!-- 여기서 param은 form 안에 value에서  param.name으로 저장해뒀다. -->
    <div  class="main__shape__success__text">${param.name}님, 회원가입이 완료되었습니다!</div>
    <form action="loginForm" method="get">
      <input type="submit"  class="main__shape__success__btn" value="로그인 화면으로 가기"><br>
    </form>
  </div>
</body>
</html>