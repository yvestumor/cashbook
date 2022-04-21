<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tagSearchRankList</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
<div>
	<a  class="btn btn-info" href="<%=request.getContextPath()%>/CashBookListByMonthController">돌아가기</a>
</div>
<h1 class="display-1">검색 결과</h1>
<h1><%=request.getAttribute("kind") %></h1>
<h1><%=request.getAttribute("aDate")%>~<%=request.getAttribute("bDate")%></h1>
<table class="table table-bordered">
	<tr>
		<th>kind</th>
		<th>tag</th>
		<th>cnt</th>
		<th>Ranking</th>
	</tr>
<%
	for(Map<String, Object> map : list) {
%>
	<tr>
		<td><%=map.get("kind")%></td>
		<td><%=map.get("tag")%></td>
		<td><%=map.get("cnt")%></td>
		<td><%=map.get("ranking")%></td>
	</tr>
<% 	
	}
%>
</div>
</table>
</body>
</html>