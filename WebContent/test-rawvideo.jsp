<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="api/video/raw/upload" enctype="multipart/form-data" method="post">
		<p>
			Select a file : <input type="file" name="file" size="45" />
		</p>
		<p>
			User_id: <input type="text" name="user_id" value="1"></input>
		</p>
		<p>
			Title: <input type="text" name="title" value="Test Title"></input>
		</p>
		<p>
			Summary: <input type="text" name="summary" value="Test Summary"></input>
		</p>
		<p>
			Tags: <input type="text" name="tags" value="狗,猫,僵尸,weibo"></input>
		</p>
		<p>
			Catalog: <input type="text" name="catalog" value="搞笑"></input>
		</p>
		<p>
			Uploader Name: <input type="text" name="uploaderName" value="wangxing"></input>
		</p>
		<input type="submit" value="Upload It" />
	</form>
</body>
</html>