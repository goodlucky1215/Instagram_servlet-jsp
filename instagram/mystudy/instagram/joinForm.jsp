<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- prefix는 내가 jstl을 꺼냈을때 <c 여기의 c가 저거임!, uri는 jstl마다 제공하는 기능들의 URI를 가져오는 것임 -->
<!-- 마지막의 /core 므로 라이브러리 core를 가져오고 주요기능은 변수지원, 흐름제어, URL 처리 등이 있다(나머지는 책 294p) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가입</title>
 <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript" defer src="idcheck.js"></script>
<link rel="stylesheet" href="joinForm.css">
</head>
<body>
		<form action="join.do" method="post"  id="main__shape">
			<div id="joinform__shape">
				<p class="joinform__title__logo" >INSTAGRAM</p>
				<p class="joinform__title__text">친구들의 사진과 동영상을 보려면 가입하세요.</p>
				<!-- join.do를 하게되면 서버에서 joinForm.jsp를 join.do로 인식: 즉, /mystudy/gasipan/join.do로 인식!이렇게!-->
				<!-- 그래서 web.xml에서 이걸로 url패핑 정보에서 찾게 됨.-->
				<p>
					아이디<br /><input type="text" class="joinform__input" name="id" value="${param.id}" oninput="idcheck()">
				<div id='message'></div>
				<div>
					<c:if test="${errors.id}">Id를 입력하세요.</c:if> 
				</div> <!-- if문 test = "조건을 여기에다가 적어줌" -->
				<!-- <c:if test="${errors.duplicateId}">이미 사용중인 아이디입니다.</c:if> 만약 map errors에 저 값이 존재하면 TURE로 되어 있을테니깐 결과를 잡아 낼 것 이다. -->
				</p>
				<p>
					이름<br /><input type="text" class="joinform__input" name="name" value="${param.name}">
					<div>
						<c:if test="${errors.name}">이름을 입력하세요.</c:if>
					</div>
				</p>
				<p>
					암호<br /><input type="password" class="joinform__input" name="password">
					<div>
						<c:if test="${errors.password}">암호를 입력하세요.</c:if>
					</div>
				</p>
				<p>
					암호 확인<br /><input type="password" class="joinform__input" name="confirmPassword">
					<div>
						<c:if test="${errors.confirmpassword}">암호 확인을 입력하세요.</c:if>
					</div>
					<div>
						<c:if test="${errors.notMatch}">암호와 암호 확인이 일치하지 않습니다.</c:if>
					</div>
				</p>
				<p>
					<input type="submit" id='signBtn' value="가입">
				</p>
			</div>
		</form>
</body>

</html>