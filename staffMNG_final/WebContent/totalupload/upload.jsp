<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/Link/head.jsp"%>
<%@ include file="/Link/scriptLink.jsp"%>
<%@ include file="/Link/cssLink.jsp"%>
<script type="text/javascript"></script>
<%@ include file="../head.jsp" %>
<%@ include file="../menu.jsp" %>
</head>
<body>
	<div class="container">
		<div class="row">
				<form action="uploadView.do" enctype="multipart/form-data"
					method="post">
					<table class="table">
						<tr>
							<td width="60">파일 :</td>
							<td width="50"><input type="button" onclick="doAddFname(); "
								value="파일찾기" /></td>
							<td width="50"><input type="text" class="form-control" name="fname"
								id="fname" style="width: 250px; height: 25px;" /></td>
							<td width="50"><input type="submit" name="upload" value="파일보기" /></td>
							<td ><input type="file" name="file" id="file"
								style="display: none"
								onchange="javascript:document.getElementById('fname').value = this.value.replace(/c:\\fakepath\\/i,'')" /></td>
							<td><a class="downSample" style="font-size: 13px;"
								href="/staffMNG/totalupload/Sample_1.xlsx" download>샘플파일
									다운로드</a></td>
						</tr>
					</table>
				</form>
		</div>

	</div>
	<div class="in_middle" width="100%">
	<c:choose>
	<c:when test="${!empty templist}">
		<%@include file="uploadView.jsp"%>
	</c:when>
	<c:otherwise>
		<%@include file="dataNotthing.jsp"%>
	</c:otherwise>
	</c:choose>
	</div>

	</div>
</body>
</html>