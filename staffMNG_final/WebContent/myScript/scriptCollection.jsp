<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

//1
var html = "<tr><td><input type=\"text\" name=\"empno\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"ename\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"birthday\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"mobile\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"holdoffice\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"zipcode\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"addr1\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"addr2\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"startdate\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"hiredate\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"updeptno\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"deptno\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"jobcode\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"remark\" value=\"\" /></td>"+
"<td><input type=\"text\" name=\"moddate\" value=\"\" /></td></tr>";

$(document).ready(function(){
	$("#btn_addtr").on("click", function(e){
	    $("#addtr").append(html);
	});
	$(".viewtd").on({
		click:function(){
			$(this).removeAttr('readonly');
			var tdtest=$(this).parent().children().eq(0).val();


		}
	});
// 	$("#viewtr").on({
// 		mouseenter:function(){
// 			$(this).css('background','yellow');
// 		},
// 		mouseleave:function(){
// 			$(this).css('background','white');
// 			var trtest=$(this).children().children().eq(0).val();
// 	}
// 	});
	
	
});

//2
	function doAddFname(text) {
		document.all.file.click();
		var fvalue = $("#fname").val().split("\\");
		var fname = fvalue[fvalue.length - 1]; // 파일명
		var fileTarget = $('.in_top #fname');
		fileTarget.on('change',	function() { // 값이 변경되면
			if (window.FileReader) { // modern browser
				var filename = this.files[0].name;
			} else { // old IE
				var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출
			} // 추출한 파일명 삽입
			$(this).siblings('#fname').val(filename);
		});
	}
</script>