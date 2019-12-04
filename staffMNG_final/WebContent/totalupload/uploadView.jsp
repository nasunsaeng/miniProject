<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<div class="form-group">
		<form name="changedValue" id="changedValue" action="uploadSave.do" method="post">
		<table class="vtable">
			<thead>
				<div class="row" data-spy="affix">
					<tr align="center">
						<td class="vhidden"><c:if test="${i.errcheck!='0'}">수정</c:if></td>
						<td class="vempno">사원번호</td>
						<td class="vename">사원명</td>
						<td class="vbirthday">생년월일</td>
						<td class="vmobile">연락처</td>
						<td colspan="3" class="vaddr">주소</td>
						<td class="vstartdate">입사일</td>
						<td class="vupdeptno">소속부서</td>
						<td class="vdeptno">소속팀</td>
						<td class="vjobcode">직급</td>
						<td class="vremark">특이사항</td>
					</tr>
				</div>
			</thead>
			<tbody>
				<c:if test="${templist!=''}">
				<form name="changedValue" action="tempupdate.do" method="post">
					<c:forEach var="i" items="${templist}">
						<form id="updateform" action="tempupdate.do" method="post">
							<tr class="viewtr" align="center">
								<td class="vhidden">
								<c:if test="${i.errcheck!='0'}">
									<button style="width:" 30px;". id="tempupdate_btn">
									<a style="font-size: 10px;">수정</a>
									</button>
									</c:if></td>

								<td class="addbtn" errcheck="${!empty i.errcheck}"><input
									type="text" class="vempno form-control" readonly="readonly"
									name="empno" value="${i.empno}" /></td>

								<td><c:choose>
										<c:when test="${!empty i.ename}">
											<input type="text" class="vename viewtd form-control"
												readonly="readonly" name="ename" empno="${i.empno}"
												value="${i.ename}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vename viewtd form-control"
												name="ename" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${!empty i.birthday}">
											<input type="text" class="vbirthday viewtd form-control"
												readonly="readonly" name="birthday" empno="${i.empno}"
												value="${i.birthday}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vbirthday viewtd form-control"
												name="birthday" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${!empty i.mobile}">
											<input type="text" class="vmobile viewtd form-control"
												readonly="readonly" name="mobile" empno="${i.empno}"
												value="${i.mobile}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vmobile viewtd form-control"
												name="mobile" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${!empty i.zipcode}">
											<input type="text" class="vzipcode viewtd form-control"
												readonly="readonly" name="zipcode" empno="${i.empno}"
												value="${i.zipcode}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vzipcode viewtd form-control"
												name="zipcode" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.addr1}">
											<input type="text" class="vaddr1 viewtd form-control"
												readonly="readonly" name="addr1" empno="${i.empno}"
												value="${i.addr1}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vaddr1 viewtd form-control"
												name="addr1" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.addr2}">
											<input type="text" class="vaddr2 viewtd form-control"
												readonly="readonly" name="addr2" empno="${i.empno}"
												value="${i.addr2}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vaddr2 viewtd form-control"
												name="addr2" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.startdate}">
											<input type="text" class="vstartdate viewtd form-control"
												readonly="readonly" name="startdate" empno="${i.empno}"
												value="${i.startdate}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vstartdate viewtd form-control"
												name="startdate" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.updeptno}">
											<input type="text" class="vupdeptno viewtd form-control"
												readonly="readonly" name="updeptno" empno="${i.empno}"
												value="${i.updeptno}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vupdeptno viewtd form-control"
												name="updeptno" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.deptno}">
											<input type="text" class="vdeptno viewtd form-control"
												readonly="readonly" name="deptno" empno="${i.empno}"
												value="${i.deptno}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vdeptno viewtd form-control"
												name="deptno" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${!empty i.jobcode}">
											<input type="text" class="vjobcode viewtd form-control"
												readonly="readonly" name="jobcode" empno="${i.empno}"
												value="${i.jobcode}" />
										</c:when>
										<c:otherwise>
											<input type="text" class="vjobcode viewtd form-control"
												name="jobcode" empno="${i.empno}"
												style="background-color: red">
										</c:otherwise>
									</c:choose></td>
								<td><input type="text" class="vremark viewtd form-control"
									readonly="readonly" name="remark" empno="${i.empno}"
									value="${i.remark}" /></td>
							</tr>
							</form>
					</c:forEach>
				</c:if>
		</table>
		<div class="in_bottom">
		<button type="button" class="btn-default" id="btn_submit" onclick="document.changedValue.submit();">제출</button>
		</div>
	</div>
</div>
</body>
</html>