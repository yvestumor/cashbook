<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import= "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TagList</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container"> 
<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");

%>
<div>
	<a class="btn btn-info" href="<%=request.getContextPath()%>/CashBookListByMonthController">돌아가기</a>
</div>
<h1 class="display-1">tag rank</h1>
<div>
<form method="post" action="<%=request.getContextPath()%>/TagSearchController">
	<div>
		<input type="radio" name="kind" value="" checked="checked">선택안함
		<input type="radio" name="kind" value="수입">수입
		<input type="radio" name="kind" value="지출">지출
	</div>
	<div>
		<input type="date" name="aDate"> ~
		<input type="date" name="bDate">
	</div>
		<button type="submit" class="btn btn-success">검색</button>
</form>
</div>
<table class="table table-hover">
	<tr>
		<th>rank</th>
		<th>tag</th>
		<th>cnt</th>
	</tr>
	<%
		for(Map<String, Object> map : list) {
	%>
		<tr>
			<td><%=map.get("rank")%></td>
			<td><a href="<%=request.getContextPath()%>/TagSameController?tag=<%=map.get("tag")%>"><%=map.get("tag")%></a></td>
			<td><%=map.get("cnt")%></td>
		</tr>
	<% 
		}
	%>
</table>
</div>
</body>
</html>