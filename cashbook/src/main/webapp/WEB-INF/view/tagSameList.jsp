<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tagSameList</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container">
<div>
		<%=session.getAttribute("sessionMemberId") %>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController">로그아웃</a>
	</div>
<h1 class="display-1"><%=request.getAttribute("tag")%>의 리스트</h1>
	<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
	%>
<div>
<a class="btn btn-info" href="<%=request.getContextPath()%>/TagController">돌아가기</a>
</div>
<table class="table table-bordered">
	<tr>
		<th>tag</th>
		<th>kind</th>
		<th>cash</th>
		<th>cashDate</th>
		<th>memo</th>
		<th>updateDate</th>
		<th>createDate</th>
	</tr>
	<%
	for(Map<String, Object> map : list) {
	%>
	<tr>
		<td><%=map.get("tag")%></td>
		<td><%=map.get("kind")%></td>
		<td><%=map.get("cash")%></td>
		<td><%=map.get("cashDate")%></td>
		<td><%=map.get("memo")%></td>
		<td><%=map.get("updateDate")%></td>
		<td><%=map.get("createDate")%></td>
	</tr>
	<% 
		}
	%>
</table>
</div>
</body>
</html>