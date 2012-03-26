<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="api/video/converted/upload" enctype="multipart/form-data" method="post">
		<p>
			Select a file : <input type="file" name="file" size="45" />
		</p>
		<p>
			User_id: <input type="text" name="user_id" value="wangxing"></input>
		</p>
		<input type="submit" value="Upload It" />
	</form>
</body>
</html>