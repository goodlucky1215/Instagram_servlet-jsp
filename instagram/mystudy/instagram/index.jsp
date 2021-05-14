<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> </title>
</head>
<body>
  <% if(session.getAttribute("authUser")!=null) { %>
 	 ${authUser.name}님, 안녕하세요!
  	<form action="logout.do" method="post">
			<input type="submit" value="로그아웃">
 	 </form>
 	 <form action="newArticleForm.jsp" method="post">
			<input type="submit" value="글 올리기">
 	 </form>
 	 <% 
 	 	if(session.getAttribute("articles")!=null){
 	 	String directory = "/upload/"; //파일을 불러올 때는 경로를  상대적인 경로로 해줘야한다!
 		List<Map<String,Object>> articles = (List<Map<String,Object>>)session.getAttribute("articles");
 	 	for(Map<String,Object> art:articles){
 	 %>
 	 	<div> 기사 </div>
 	 	<div> <%= art.get("fileNo") %></div>
 	 	<div> <img src=<%= directory+art.get("fileName") %> /></div>
 	 	<div> <%= art.get("memberid") %></div>
 	 	<div> <%= art.get("contentText") %></div>
 	 	<div> <%= art.get("read_cnt") %></div>
 	 <% } 
 	 }%>
 <% } else { %>
 	  <form action="index.jsp" method="post">
 <% } %>
</body>
</html>