<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${sessionMemberId}님의 정보 수정</h1>
	<form method="post" action="${pageContext.request.contextPath}/updateCashbookMember">
		<c:forEach var="l" items="${list}">
		<table>
			<tr>
				<th>사용자 비밀번호</th>
				<td>
					<input type="text" name="memberPw">
				</td>
			</tr>
		</table>
			<button  type="submit">비밀번호 수정</button>
		</c:forEach>
	</form>
</body>
</html>