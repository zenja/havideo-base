<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.zenja.havideo.user.services.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% if(UserService.checkUser("test", "test")) { %>
	<p>checkUser("test", "test") returns true!</p>
	<% } else { %>
	<p>checkUser("test", "test") returns false!</p>
	<% } %>
	
	<% if(UserService.checkUser("test", "wrong_password")) { %>
	<p>checkUser("test", "test") returns true!</p>
	<% } else { %>
	<p>checkUser("test", "test") returns false!</p>
	<% } %>
	
	<% if(UserService.isUserExists("test")) { %>
	<p>UserService.isUserExists("test") returns true!</p>
	<% } else { %>
	<p>UserService.isUserExists("test") returns false!</p>
	<% } %>
	
	<% if(UserService.isUserExists("no_this_user")) { %>
	<p>UserService.isUserExists("test") returns true!</p>
	<% } else { %>
	<p>UserService.isUserExists("test") returns false!</p>
	<% } %>
	
	<p>Adding user "zenja"...<p>
	<% UserService.addUser("zenja", "zenja"); %>
	<% if(UserService.isUserExists("zenja")) { %>
	<p>UserService.isUserExists("zenja") returns true!</p>
	<% } else { %>
	<p>UserService.isUserExists("zenja") returns false!</p>
	<% } %>
	
	<p>Deleting user "zenja"...<p>
	<% UserService.deleteUser("zenja"); %>
	<% if(UserService.isUserExists("zenja")) { %>
	<p>UserService.isUserExists("zenja") returns true!</p>
	<% } else { %>
	<p>UserService.isUserExists("zenja") returns false!</p>
	<% } %>
</body>
</html>