<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자 로그인 페이지</title>
<%@ include file="head.jsp" %>
<style>
	.logo{
		 padding: 10px;
		 width: 100%;
		 text-align: center;
	}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$("#btnLogin").click(function(){
		$("#formLogin").attr("action","${path}/login.fo");
		$("#formLogin").submit();
	});
});
</script>
</head>
<body>

<!-- <div class="logo">
	<img src="../Image/logo.png"/>
	<br/>
</div> -->
<div class="background">
</div>
<div class="container">
<h2>사원 관리자 인증</h2>
<p>000회사 사원 관리 페이지 입니다. <code>관리자가 아닐시 회사내규에 따라 처벌됩니다.</code></p>
<p>관리자 아이디와 비밀번호를 정확히 입력해 주세요.</p>
<form name="formLogin" id="formLogin">
    <div class="form-group">
      <label for="uname">관리자 아이디:</label>
      <input type="text" class="form-control" id="userid" placeholder="Enter username" name="userid" required>
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호:</label>
      <input type="password" class="form-control" id="passwd" placeholder="Enter password" name="passwd" required>
    </div>
    <div class="form-group form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox" name="remember" required>아이디 저장
      </label>
    </div>
    <button type="button" class="btn btn-primary" id="btnLogin">로그인</button>
    <div id="loginMsg" style="color:red;">
    	<c:if test="${param.message=='error'}">id 또는 패스워드가 틀립니다. 다시 로그인 하세요</c:if> 
    </div>
</form>
</div>
</body>
</html>

