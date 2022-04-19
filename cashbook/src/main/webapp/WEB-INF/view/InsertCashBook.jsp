<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InsertCashBook</title>
</head>
<body>
	<form method="post" action="<%=request.getContextPath()%>/InsertCashBookController">
	<table border="1">
	<thead>
		<tr>
			<th>수입,지출</th>
			<th>금액</th>
			<th>메모</th>
		</tr>
		<tr>
		
		</tr>
	</thead>
		<tbody>
			<tr>
				<td>
				 <select name="kind">
					  <option value="수입">수입</option>
					  <option value="지출">지출</option>
		 		</select>
		 		</td>
	 			<td>
	 				<input type="number" name="cash"> 
	 			</td>
	 			<td>
	 				<input type="text" name="memo">
	 			</td>
	 		</tr>
	 	</tbody>
	</table>
	<button type="submit">입력</button>
	</form> 
</body>
</html>