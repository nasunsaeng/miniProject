<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function(){
		$(".mytr").css("cursor","pointer");
		
		/* 좌측 리스트 클릭 -> 상세조회 */
		$(".mytr").click(function(){
			var empno=$(this).children().eq(1).text();
			var param={"empno":empno,"flag":"searchList"};
			$.ajax({
				url:"/Staff_teamwork/memberDetail.fo",
				type: "post",
				data: param,
				success: function(result){
					$("#contentview").html(result);
				}
			});			
		});
		
		/* 체크박스 클릭시 부모테그 이벤트 제거 */
 		$(".chkbox").click(function(e){
			var rnum = $(this).attr("id");
			$("#"+rnum).attr("checked","true");
// 			e.preventDefault();	기본이벤트제거
			e.stopPropagation(); // 부모태그 이벤트제거
			
		}); 
		/* 전체선택 또는 선택취소 */
		$("#allchk").click(function(){
			var chk=$(this).is(":checked");
			if(chk){
				$(".chkbox").prop("checked",true);
			}else{
				$(".chkbox").prop("checked",false);
			}
		});
		
		$('#excel_btn').click(function(){
			var elements = [];
			var searchOption=$("#searchOption").val();
			var searchWord=$("#searchWord").val();
			var exceltypes = '';
			
			if ($('input:radio[name="exceltype"]:checked').length == 0) {
				if ($('input:checkbox[name="element"]:checked').length == 0) {
					alert("선택된 엑셀 타입과 체크박스가 없습니다. !");
					return;
				} else {
					$('input:checkbox[name="element"]:checked').each(function() {
						alert("선택된 엑셀 타입이 없습니다. !");
						elements.push($(this).val());
					});
				}
				return;
			} else {
				$('input:radio[name="exceltype"]:checked').each(function() {
					exceltypes = $('input:radio[name="exceltype"]:checked').val();
					if ($('input:checkbox[name="element"]:checked').length == 0) {
						alert("선택된 체크박스가 없습니다. !");
						return;
					} else {
						$('input:checkbox[name="element"]:checked').each(function() {
							elements.push($(this).val());
						});
					}
				});
			}
			var outputparam = {
					"elements" : elements,
					"exceltype" : exceltypes,
					"curPage" : curPage,
					"flag" : "searchList",
					"searchOption":searchOption,
				    "searchWord":searchWord};
			$.ajax({
				url : "${path}/outputexcel.do",
				type : "post",
				data : outputparam,
				success : function(result) {
					alert("elements = "+result);
					$('#myModal').hide();
				}
			});
		});
	});
	//체크된 항목 삭제
	function doDelete(){
		var empno = [];
		var curPage=${current_page};
		var searchOption=$("#searchOption").val();
		var searchWord=$("#searchWord").val();
		
		if( $('input[name="chkDel"]:checked').length == 0){
			alert("선택된 체크박스가 없습니다. !");
			return;
		}else{
			$('input[name="chkDel"]:checked').each(function() {
				empno.push($(this).val());
			});
		}			
 		if(confirm("선택한 항목을 삭제하시겠습니까?")){
			var param={"empno":empno,
			           "curPage":curPage,
			           "flag":"searchList",
			           "searchOption":searchOption,
			           "searchWord":searchWord};
			$.ajax({
				url:"/Staff_teamwork/memberDelete.fo",
				type: "post",
				data: param,
				success: function(result){
					$("#emplist").html(result);
				}
			});
		} 
	}
	//모달띄우기
	function doModal() {
		
		var empno = [];
		var curPage=${current_page};
		var searchOption=$("#searchOption").val();
		var searchWord=$("#searchWord").val();

		if ($('input[name="chkDel"]:checked').length == 0) {
			alert("선택된 체크박스가 없습니다. !");
			return;
		} else {
			$('input[name="chkDel"]:checked').each(function() {
				empno.push($(this).val());
			});
		}
		var param={
				"empno":empno,
		        "curPage":curPage,
		        "flag":"searchList",
		        "searchOption":searchOption,
		        "searchWord":searchWord
				};
		$.ajax({
			url : "${path}/maindbselect.do",
			type : "post",
			data : param,
			success : function(result) {
				alert("와라 제발");
				$('#modal_btn').click();
			},
			error : function(){
				alert("젠장");
			}
		});
}
</script>
<%@ include file="session_check.jsp" %>
</head>
<body>
<form id="myform">
	<div class="col-xs-12">
		<!-- 중간 -->
<!-- 		<div class="row"> -->
			<h2>사원 리스트</h2>
			
<!-- 			<div class="col-xs-12"> -->
				<div class="col-md-4">
				<button type="button" style="margin: 10px 0;" class="btn" onclick="doDelete();">선택삭제</button>
				<button type="button" style="margin: 10px 0;" id="btn_pexcel"
					class="btn" onclick="doModal()">선택출력</button>
				<button type="button" style="display: none" id="modal_btn"
					class="btn btn-info btn-lg" data-toggle="modal"
					data-target="#myModal">Open Modal</button>
				</div>
			</div>
		<div class="row"><!-- xs( xm md lg -->
			<div class="col-xs-12">
				<table class="table table-hover table-bordered">
					<tr>
						<td><input type="checkbox" id="allchk"></td>
						<td>사원번호</td>
						<td>이름</td>
						<td>부서</td>
						<td>팀</td>
						<td>직급</td>
						<td>입사날짜</td>
					</tr>
			<c:forEach items="${myList}" var="i">
					<tr class="mytr">
						<th class="justify-content-center text-center">
							<input class="chkbox" type="checkbox" name="chkDel" value="${i.empno}"/>
						</th>
						<td>${i.empno}</td>
						<td>${i.ename}</td>
						<td>${i.deptno}</td>
						<td>${i.updeptno}</td>
						<td>${i.jobcode}</td>
						<td>${i.startdate}</td>
					</tr>
			</c:forEach>
				</table>
			</div>
		</div>
		<div class="row">
			<table width="650px">
				<tr align="center">
					<td colspan="2">
						<c:if test="${current_page > 1}">
							<a href="#" onclick="searchList('1')">[처음]</a>
						</c:if>
						<c:if test="${current_block >1}">
							<a href="#" onclick="searchList('${prev_page}')">[이전]</a>
						</c:if>
						<c:forEach var="page" begin="${block_start}" end="${block_end}">
						  <c:if test="${page == current_page}">
							  <span style="color:red;">[${page}]</span>
						  </c:if>	
						  <c:if test="${page != current_page}">
							  <a href="#" onclick="searchList('${page}')">[${page}]</a>
						  </c:if>	
						</c:forEach>
						<c:if test="${current_block < total_block}">
							<a href="#" onclick="searchList('${next_page}')">[다음]</a>
						</c:if>
						<c:if test="${current_page < total_page}">
							<a href="#" onclick="searchList('${total_page}')">[끝]</a>&nbsp;&nbsp;
							<input type="hidden" id="currentPage" value="${current_page}">${current_page}/${total_page} page
						</c:if>
					</td>
				</tr>
			</table>
		</div>
</form>
<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4>엑셀로 출력하기</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<form name="modalvalue" action="outputexcel.do" method="post">
						<div class="modal-body">
								<p>
									<a>버전 선택</a> &nbsp;&nbsp; <input type="radio" name="exceltype"
										id="exceltype" class="" value="xlsx" /> 2007년도 이후 버전 <input
										type="radio" name="exceltype" id="exceltype" class=""
										value="xls" /> 2007년도 이전 버전
								</p>
								<table class="table">
									<tr>
										<td class="chkelement"><input type="checkbox"
											name="element" value="empno" />&nbsp;사&nbsp; 번</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="ename" />&nbsp;이&nbsp; 름</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="birthday" />생년월일</td>
									</tr>
									<tr>
										<td class="chkelement"><input type="checkbox"
											name="element" value="mobile" />&nbsp;연락처</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="holdoffice" />&nbsp;재직여부</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="addr" />&nbsp;주 &nbsp;소</td>
									</tr>
									<tr>
										<td class="chkelement"><input type="checkbox"
											name="element" value="startdate" />&nbsp;입사일</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="hiredate" />&nbsp;퇴사일</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="updeptno" />&nbsp;부서명</td>
									</tr>
									<tr>
										<td class="chkelement"><input type="checkbox"
											name="element" value="deptno" />&nbsp;팀 &nbsp;명</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="jobcode" />&nbsp;직 &nbsp;급</td>
										<td class="chkelement"><input type="checkbox"
											name="element" value="moddate" />&nbsp;특이사항</td>
								</table>
						</div>
					</form>
					<div class="modal-footer">
						<button id="excel_btn" class="btn">내려받기</button>
						<button type="button" class="btn" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
</body>
</html>


