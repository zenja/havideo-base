<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.zenja.havideo.hdfs.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test HDFS</title>
</head>
<body>
	<form action="api/hdfs/upload" enctype="multipart/form-data" method="post">
		<p>
			Select a file : <input type="file" name="file" size="45" />
		</p>
		<p>
			Destination: <input type="text" name="destination"></input>
		</p>
		<input type="submit" value="Upload It" />
	</form>
</body>
</html>