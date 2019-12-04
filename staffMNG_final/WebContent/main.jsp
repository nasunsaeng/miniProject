﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<%@ include file="head.jsp" %>
<%@ include file="menu.jsp" %>

<!-- 다움주소서비스 -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>

$(document).ready(function(){
	list(1);
});
function list(curPage){
	var param={"curPage":curPage};
	$.ajax({
		type:"post",
		url:"${path}/list.fo",
		data:param,
		success:function(result){
			$("#emplist").html(result);
		}
	});		
}
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

<div class="col-md-7">
	<div class="col text-center" id="emplist"></div>
</div>

<div class="col-md-5">
	<div class="col text-center" id="contentview">
 		<%@ include file="memberDetail.jsp" %> 
	</div>
</div>

<div class="row">
	<div class="col text-center">
		copy right
	</div>
</div>
</body>
</html>