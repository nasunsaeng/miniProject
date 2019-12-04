<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script>
$(document).ready(function(){
	$("#deptno").val("${dto.deptno}");  //selcet박스-부서 
	$("#jobcode").val("${dto.jobcode}"); //selcet박스-직급
	
	$("#btnUpdate").click(function(){
		if(confirm("수정하시겠습니까?")){
			var curPage=$("#currentPage").val();
			var flag="${param.flag}";
			
			var param=$("#form1").serialize()+"&curPage="+curPage;
			$.ajax({
				url: "memberUpdate.fo",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "text",			
				data: param,
				success: function(result){
					$("#contentview").html(result);
					if(flag=="list"){
						list(curPage);
					}else{
						searchList(curPage);
					}
					
					alert("수정되었습니다.");			
				}
			}); 		
		}
	});
	
 	$("#btnAddr").on('click',function(e){
		showPostcode();
	}); 	
})
</script>
<%@ include file="session_check.jsp" %>
<body>

<form id="form1" name="form1">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-12 text-center" style="margin: 13px 0;">
				<h2>사원세부내용</h2><br/><br/>
				<table class="table table-boardered">
					<tr>
						<th>사번</th>
						<td><input type="text" class="form-control" name="empno" value="${dto.empno}" readonly="readonly"></td>
					</tr>
					<tr>
						<th>사원명</th>
						<td><input type="text" class="form-control" name="ename" value="${dto.ename}"></td>
					</tr>

					<tr>
						<th>생년월일</th>
						<td><input type="text" class="form-control" name="birthday" value="${dto.birthday}"></td>
					</tr>

					<tr>
						<th>전화번호</th>
						<td><input type="tel" class="form-control" name="mobile" value="${dto.mobile}"></td>
					</tr>

					<tr>
						<th>주소</th>
						<td align="left">
							<input type="text" name="zipcode" id="zipcode" value="${dto.zipcode}" 
								style="width:90px;border-radius:5px;border-style:groove;border-color:#efefef;">&nbsp; 
							<input type="button" id="btnAddr" value="주소찾기" border-radius:5px;border-style:groove;border-color:#ebebeb;><br>                               
							<input type="text" name="addr1" id="addr1" value="${dto.addr1}" 
								style="width:450px;border-radius:5px;border-style:groove;border-color:#f5f5f5;"><br>
							<input type="text" name="addr2" id="addr2" value="${dto.addr2}" 
								style="width:450px;border-radius:5px;border-style:groove;border-color:#f5f5f5;">
						</td>
					</tr>

					<tr>
						<th>입사날짜</th>
						<td><input type="text" class="form-control" name="startdate" value="${dto.startdate}"></td>
					</tr>

					<tr>
						<th>퇴사날짜</th>
						<td><input type="text" class="form-control" name="hiredate" value="${dto.hiredate}"></td>
					</tr>
					<tr>
						<th>부서</th>
						<td><select class="form-control" name="deptno" id="deptno">
							<option value="0001">관리부</option>
							<option value="0005">영업부</option>
							<option value="0009">생산부</option>
							<option value="0007">연구소</option>
						</select></td>
					</tr>

					<tr>
						<th>팀</th>
						<td><select class="form-control" name="updeptno" id="updeptno">
							<option value="1000">재무/회계</option>
							<option value="2000">인사/총무</option>
							<option value="3000">교육관리</option>
							<option value="4000">영업</option>
							<option value="5000">영업지원</option>
							<option value="6000">생산계획/QC</option>
							<option value="7000">A/S</option>
							<option value="8000">연구소</option>
						</select></td>
					</tr>

					<tr>
						<th>직급</th>
						<td><select class="form-control" name="jobcode" id="jobcode">
							<option value="001">대표이사</option>
							<option value="005">부장</option>
							<option value="006">차장</option>
							<option value="007">과장</option>
							<option value="010">대리</option>
							<option value="020">주임</option>
							<option value="030">사원</option>
						</select></td>
					</tr>

					<tr>
						<th>특이사항</th>
						<td><textarea rows="5" cols="40" name="remark" id="remark"
								class="form-control">${dto.remark}</textarea></td>
					</tr>

					<tr>
						<td colspan="2">
							<input type="button" class="btn btn-primary" id="btnUpdate" value="수정"> 
							<!-- <input type="reset"	class="btn btn-danger" value="취소"> -->
							</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
		
</body>
</html>