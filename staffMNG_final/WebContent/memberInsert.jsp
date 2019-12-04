<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>사원 등록 페이지</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<!-- 다움주소서비스 -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<%@ include file="head.jsp" %>
<%@ include file="menu.jsp" %>
<script>
$(document).ready(function(){
	$("#btnSave").click(function(){
		if(confirm("등록 하시겠습니까?")){
			var param=$("#formInsert").serialize()
			$.ajax({
				url: "memberWrite.fo",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "text",			
				data: param,
				success: function(result){
					alert(result);	
					//$("#message").html();		
				}
			}); 
		}
	});
	
 	$("#btnAddr").on('click',function(e){
		showPostcode();
	}); 	
})

//다음주소서비스
function showPostcode(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
            //각 주소의 노출 규칙에 따라 주소를 조합한다. 
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fulladdr = '';   // 주소 변수
            var extraAddr = '';   // 참고항목 변수
            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fulladdr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fulladdr = data.jibunAddress;
            }
            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. 
                  if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있는 경우 추가한다
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형 주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fulladdr += (extraAddr !== ''?'(' + extraAddr + ')':'');
             }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("addr1").value = fulladdr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addr2").focus();
        }
    }).open();	
}
</script>
<%@ include file="session_check.jsp" %>
</head>
<body>
	<div class="container text-center" style="height: 1000px; width: 800px;">
		<div class="col-sm-12 text-center">
			<h2 style="margin:30px">사원 등록 페이지</h2>
			<form name="formInsert" id="formInsert">
				<table class="table table-boardered" >
					<tr>
						<th>사번</th>
						<td><input type="text" class="form-control" name="empno"></td>
					</tr>
					<tr>
						<th>사원명</th>
						<td><input type="text" class="form-control" name="ename"></td>
					</tr>
					<tr>
						<th>생년월일</th>
						<td><input type="text" class="form-control" name="birthday"></td>
					</tr>

					<tr>
						<th>전화번호</th>
						<td><input type="tel" class="form-control" name="mobile"></td>
					</tr>

					<tr>
						<th>주소</th>
						<td align="left">
							<input type="text" name="zipcode" id="zipcode" style="width:90px;">&nbsp;
							<input type="button" id="btnAddr" value="주소찾기"><br>
							<input type="text" name="addr1" id="addr1" style="width:650px;"><br>
							<input type="text" name="addr2" id="addr2" style="width:650px;">								
						</td>
					</tr>

					<tr>
						<th>입사날짜</th>
						<td><input type="text" class="form-control" name="startdate"></td>
					</tr>

					<tr>
						<th>퇴사날짜</th>
						<td><input type="text" class="form-control" name="hiredate"></td>
					</tr>
					<tr>
						<th>부서</th>
						<td><select name="job" class="form-control" name="deptno">
							<option value="0001">관리부</option>
							<option value="0005">영업부</option>
							<option value="0009">생산부</option>
							<option value="0007">연구소</option>
						</select></td>
					</tr>
					<tr>
						<th>팀</th>
						<td><select name="job" class="form-control" name="updeptno">
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
						<td><select name="job" class="form-control" name="jobcode">
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
						<td><textarea rows="5" cols="40" name="remark" class="form-control"></textarea></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" class="btn btn-primary" value="등록" id="btnSave"> 
						<input type="reset"	class="btn btn-danger" value="reset"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>