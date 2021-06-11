<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%  HashMap<String,String> map = (HashMap<String,String>)request.getAttribute("articleread"); %>
<%  String error = (String)request.getAttribute("error"); %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>InstagramView</title>
  <link rel="stylesheet" href="/css/articleView.css?v=<%=System.currentTimeMillis()%>">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" defer src="/css/articleRead.js?v=<%=System.currentTimeMillis()%>"></script>
</head>

<body>
	<header id="header__shape">
	    <div  id="header__shape__width">
	      <h2 class="header__text__font">
	        Instagram
	      </h2>
	        <div class="header__text__find__input">
	          	<form action="mainview" method="post">
	       			<input type="submit" class='btn' value="메인화면 바로가기">
	      		</form>
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
			<div class="articles__shape__width">
				<div class="articles__box">
					<div class="articles__id__form">
						<div class="articles__id" name="memberid" value=${articleread.memberid}>👤 ${articleread.memberid}</div>
	      				<form action="delete?no=${articleread.fileNo}" method="post">
	        				<input type="submit" class='btn' value="삭제">
	        				<% if(error!=null) { %>
	        					<script type="text/javascript"> 
	        						alert("<%= error %>");
	        			   		</script> 
	        			   <%  } %>
	      				</form>
					</div>
					<img class="articles__img" src="/upload/${articleread.fileName}">
					<div class="articles__bottom">
						<div class="articles__bottom__heart" onclick="heartClick(${articleread.fileNo})" id="${articleread.fileNo}"  name="fileNo" value=${articleread.fileNo}>
							<%	if("0".equals(map.get("heart"))) { %>
								🤍
							<% } else { %>
								💗
							<% } %>
						</div>
						<div class="articles__bottom__heart__num" id="num__${articleread.fileNo}">${articleread.read_cnt}</div>
						<div class="articles__bottom__text">${articleread.contentText}</div>
					</div>
					<div class="articles__space"></div>
				</div>
			</div>
  		</section>


</body>
</html>