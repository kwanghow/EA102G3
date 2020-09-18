<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<style type="text/css">

</style>
<title>Join Us</title>
</head>
<body>
	<div id="outPopUp">
		<h1 align="center">上線囉～</h1>
		<form id="myForm" action="<%=request.getContextPath() %>/chat.do" method="POST">
			<input id="memId" name="memId" class="text-field" type="text" placeholder="Input  memId" /> <br>
			<input id="reciver" name="reciver" class="text-field" type="text" placeholder="Input  reciver" /> <br>
 
			<input type="submit" id="send" class="button" value="送出" onclick="sendName();" />
		</form>
	</div>
</body>
<script>

	
	function sendName() {
		var userName = inputUserName.value.trim();
		if (userName === "") {
/* 			alert("Input a user name");
			inputUserName.focus();
			return; */
		} else {
			document.getElementById("myForm").submit();
		}
	}
</script>

</html>