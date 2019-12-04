<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function(){
	$("#btnSearch").click(function(){
		searchList(1);
	});
});
function searchList(curPage){
	var param=$("#searchForm").serialize()+"&curPage="+curPage;
	$.ajax({
		url: "/Staff_teamwork2/search.fo",
		type: "post",
		data: param,
		success: function(result){
			 $("#emplist").html(result); 
		}
	});	
} 
</script>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<!-- 메뉴에서 가장 왼쪽, 모바일에서 표시되는 제목 -->
		<div class="navbar-header">
			<!-- 모바일때 표시되는 메뉴 버튼(PC 버젼에서는 보이지 않는다.) -->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- 메뉴의 홈페이지 이름 -->
			<a class="navbar-brand" style="cursor: pointer"
				onclick="location.href='${path}/main.fo';">staffMNG</a>
		</div>
		<!-- 메뉴의 리스트 -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!-- Link 메뉴 (class가 active가 포함되어 있기 때문에 선택된 메뉴 뜻) -->
				<li><a href="#" onclick="location.href='${path}/main.fo';">사원목록</a></li>
				<li><a href="#" onclick="location.href='${path}/memberInsert.fo';">사원등록</a></li>
				<!-- Link 메뉴 -->
				<li><a href="#" onclick="location.href='${path}/totalupload/upload.do';">사원일괄등록</a></li>

			</ul>

			<form class="navbar-form navbar-left" name="searchForm" id="searchForm">
				<select name="searchOption" id="searchOption" class="form-control">
					<option value="ename">이름</option>
					<option value="deptno">부서</option>
				</select>
				<div class="form-group">
					<input type="text" class="form-control" name="searchWord" id="searchWord">
				</div>
				<button type="button" class="btn btn-default" id="btnSearch">검색</button>
			</form>

			<!-- 오른쪽 정렬의 메뉴 -->
			<ul class="navbar-right" style="list-style:none;">
				<li ></li>
				<li>
					<c:choose>
						<c:when test="${sessionScope.userid == null }">
							<a href="${path}/main.fo">로그인</a> | 
						</c:when>
						<c:otherwise>
							<c:if test="${sessionScope.userid != null }">
								${sessionScope.userid}님이 로그인 하셨습니다. | 
							</c:if> 
							<a href="${path}/logout.fo">로그아웃</a>
						</c:otherwise>
					</c:choose>				
				</li>
			</ul>
		</div>
	</div>
</nav>





