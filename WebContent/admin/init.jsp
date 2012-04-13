<%@page import="cn.edu.nju.software.liushuai.storage.service.ServiceConst"%>
<%@page import="cn.edu.nju.software.liushuai.storage.service.InitOption"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初次部署界面</title>
</head>
<body>
<h1>管理员用命令</h1>
<%
String value = new InitOption().getStatus();
out.println(value);
%>
<p/>
<h2>重置</h2>
<form action="<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/init/do" method="post">
<input type="submit" value="开始重置"/>
</form>
</body>
</html>