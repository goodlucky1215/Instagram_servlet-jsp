<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> </title>
<%  
	response.setHeader("Pragma", "no-cache"); 
	response.setHeader("Cache-Control", "no-cache"); 
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0L);
%>
</head>
<body>
  ${authUser.name}님, 안녕하세요!
  <form action="logout.do" method="post">
		<input type="submit" value="로그아웃">
  </form>
  
</body>
</html>