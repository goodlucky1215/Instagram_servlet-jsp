<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
<script type="text/javascript" defer src="upload.js?v=<%=System.currentTimeMillis()%>"></script>
<link rel="stylesheet" href="upload.css">
</head>
<body>
  <section id="main__shape">
    <!-- multipart리퀘스트라는 클래스와 매칭이 되는 하나의 타입   이걸로 여러개의 파일이 폼태그로 전달이 될 수 있다.-->
      <form action="upload.do" method="post" enctype="multipart/form-data" frm.encoding = "application/x-www-form-urlencoded"  class="upload__shape">
        <div>
          파일: <input type="file" id="file__input" name="file" onchange="previewFile()"><br>
        </div>
        <div>
          <div id="img__view__size">
            <img id="img__view__img" src="">
          </div>
          <div class="error">${message}</div>
        </div>
        <div id="textarea__shape">
          <textarea id="textarea" name="content" rows="5" cols="40" onKeyUp="textCnt()"></textarea>
          <div id="textcnt">(0 / 100)</div>
        </div>
        <div id="uploadBtn__shape">
          <input id="uploadBtn" type="submit" value="업로드"><br>
        </div>
      </form>
  </section>
</body>
</html>