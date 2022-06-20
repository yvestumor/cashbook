<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가계부수정</title>
</head>
<body>
<div>
	<%=session.getAttribute("sessionMemberId") %> 님 반갑습니다. 
	<a href="<%=request.getContextPath()%>/LogoutController">로그아웃</a>
</div>
	<form method="post" action="${pageContext.request.contextPath}/updateCashbookController">
		<input type="number" name="cashbookNo" value="${cashbookNo}" hidden="hidden">
		<table border="1">
			<c:forEach var="l" items="${list}">
				<tr>
					<td>kind</td>
					<td>
						<select name="kind">
							<option value="수입" <c:if test="${l.kind eq '수입'}">selected</c:if>>수입</option>
							<option value="지출" <c:if test="${l.kind eq '지출'}">selected</c:if>>지출</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>cash</td>
					<td>
						<input type="number" name="cash" value="${l.cash}">
					</td>
				</tr>
				<tr>
					<td>memo</td>
					<td>
						 <textarea rows="4" cols="50" name="memo" >${l.memo}</textarea>
					</td>
				</tr>
			</c:forEach>			
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>