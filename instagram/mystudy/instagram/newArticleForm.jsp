<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
<script type="text/javascript" defer src="upload.js"></script>
</head>
<body>
<!-- multipart리퀘스트라는 클래스와 매칭이 되는 하나의 타입   이걸로 여러개의 파일이 폼태그로 전달이 될 수 있다.-->
	<form action="upload.do" method="post" enctype="multipart/form-data" frm.encoding = "application/x-www-form-urlencoded">
		파일: <input type="file" name="file" onchange="previewFile()"><br>
		<textarea id="textarea" name="content" rows="10" cols="50" onKeyUp="textCnt()"></textarea>
		<div id="textcnt">(0 / 100)</div>
		<input type="submit" value="업로드"><br>
	</form>
	<img src="" height="200">
	<div>${message}</div>
</body>
</html>