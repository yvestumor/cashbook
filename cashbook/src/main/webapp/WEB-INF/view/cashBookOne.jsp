<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="vo.Cashbook" %>
<%@ page import ="java.util.*" %>
<%
	List<Cashbook> list = (List<Cashbook>)request.getAttribute("list");	
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashbookOne</title>
</head>
<body>
<div>
		<%=session.getAttribute("sessionMemberId") %>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController">로그아웃</a>
	</div>
<h1>상세보기</h1>
	<table border ="1">
		<%
			for (Cashbook c : list) {
				
		%>
			<tr>
				<td>CashbookNo</td>
				<td><%=c.getCashbookNo()%></td>
			</tr>
			<tr>
				<td>cashDate</td>
				<td><%=c.getCashDate()%></td>
			</tr>
			<tr>
				<td>kind</td>
				<td><%=c.getKind()%></td>
			</tr>
			<tr>
				<td>cash</td>
				<td><%=c.getCash()%></td>
			</tr>
			<tr>
				<td>memo</td>
				<td><%=c.getMemo()%></td>
			</tr>
			<tr>
				<td>updateDate</td>
				<td><%=c.getUpdateDate()%></td>
			</tr>
			<tr>
				<td>createDate</td>
				<td><%=c.getCreateDate()%></td>
			</tr>
	</table>
	<div>
		<a href ="<%=request.getContextPath()%>/DeleteCashBookController?cashbookNo=<%=c.getCashbookNo()%>">삭제</a>
		<a href ="<%=request.getContextPath()%>/UpdateCashBookController?cashbookNo=<%=c.getCashbookNo()%>">수정</a>
	</div>
		<%
			}
		%>	
</body>
</html>