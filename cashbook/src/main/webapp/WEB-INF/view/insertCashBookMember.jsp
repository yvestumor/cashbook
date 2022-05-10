<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.helper {
		color: #FF0000;
	}
</style>
</head>
<body>
<h1>회원가입</h1>
<form id ="signupForm" method="post" action="<%=request.getContextPath()%>/InsertMemberController">
	<div>
		<table border="1">
			<tr>
				<td>ID입력(4자이상)</td>
				<td>
					<input type="text" id="id" name="memberId">
					<span id="idHelper" class="helper"></span>
				</td>
			</tr>
			<tr>
				<td>
				<div>비밀번호 입력(4자이상)</div>
				<div>비밀번호 확인</div>
				</td>
				<td>
					<div>
						<input type="password" id="pw" name="memberPw" value="">  
					</div>
					<div>
						<input type="password" id="pwConfirm" name="pwConfirm" value="">
					</div>
					<span id="pwHelper" class="helper"></span>					
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="signup">signup</button>
				</td>
			</tr>
		</table>
	</div>
</form>
</body>
<script>
	document.querySelector('#id').focus();
	// id 입력창 검사
	document.querySelector('#id').addEventListener('blur', function(){
		if(document.querySelector('#id').value.length < 4) {
			console.log('id는 4자 이상입력'); // 디버깅
			// 일반태그는 텍스트값을 innerHTML속성 사용
			document.querySelector('#idHelper').innerHTML = 'id는 4자 이상 입력해주세요';
			document.querySelector('#id').focus(); // 커서를 다시 id입력창으로 이동
		} else {
			document.querySelector('#idHelper').innerHTML = '';
		}
	});
	
	// 2) pw와 pw확인 입력창 검사
	document.querySelector('#pwConfirm').addEventListener('blur', function(){
		if(document.querySelector('#pw').value.length < 4) {
			console.log('pw는 4자 이상'); // 디버깅
			document.querySelector('#pwHelper').innerHTML = 'password는 4자 이상입력해주세요';
			document.querySelector('#pw').focus();
		} else if (document.querySelector('#pw').value
						!= document.querySelector('#pwConfirm').value) {
			document.querySelector('#pwHelper').innerHTML = 'password가 다릅니다';
			document.querySelector('#pw').focus();
		} else {
			document.querySelector('#pwHelper').innerHTML = '';
		}
	});
	
	
	// 3) 버튼을 클릭했을 때 유효성 검사 
	document.querySelector('#signup').addEventListener('click', function(){
		
		if(document.querySelector('#id').value.length < 4) {
			document.querySelector('#idHelper').innerHTML = 'id는 4자 이상 입력해주세요';
			document.querySelector('#id').focus();
		} else if(document.querySelector('#pw').value.length < 4) {
			document.querySelector('#idHelper').innerHTML = '';
			document.querySelector('#pwHelper').innerHTML = 'password는 4자 이상입력해주세요';
			document.querySelector('#pw').focus();
		} else {
			document.querySelector('#signupForm').submit();
		}
	});
	
</script>
</html>