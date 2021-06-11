<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>화면</title>
  <link rel="stylesheet" href="/css/articleView.css">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" defer src="/css/articleView.js?v=<%=System.currentTimeMillis()%>"></script>
</head>

<body>
	<header id="header__shape">
	    <div  id="header__shape__width">
	      <h2 class="header__text__font">
	        Instagram
	      </h2>
	        <div class="header__text__find__input">
	          <input type="text" class="header__text__find__input__findtext" name="findtext">
	          <input type="submit" class='btn header__text__find__input__btn' onclick="findtext()" value="검색">
	        </div>
	      <div class="header__text">
	        ${authUser.name}님, 안녕하세요!
	      </div>
	      <form action="logout" method="post">
	        <input type="submit" class='btn' value="로그아웃">
	      </form>
	      <form action="upload" method="get">
	        <input type="submit" class='btn' value="글 올리기">
	      </form>
	    </div>
	  </header>
 	   <section id="articles__shape">

  		</section>


</body>
</html>