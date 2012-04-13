<%@page import="cn.edu.nju.software.liushuai.storage.service.ServiceConst"%>
<%@page import="cn.edu.nju.software.liushuai.storage.bean.CatalogBean"%>
<%@page import="java.util.List"%>
<%@page import="cn.edu.nju.software.liushuai.storage.exception.SeeGodException"%>
<%@page import="cn.edu.nju.software.liushuai.storage.service.AdminOption"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%
    AdminOption adminOption = new AdminOption();
    List<CatalogBean> catalogList = adminOption.getCatalogs();
    String modifyId = request.getParameter("modify");
    %>
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dlabel">
<html>
<head>
<style type="text/css">
label {display:inline-block;}
span {display:inline-block; padding-right:5px}
span.name {width:10ex;}
span.children {width:20ex;}
span.position {width:7ex;}
input {display:inline-block; width:inherit;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin-分类管理</title>
</head>
<body>
<h1>分类列表</h1>
<h5>已有分类</h5>
<%
for(CatalogBean bean :catalogList) {
	if(modifyId != null && modifyId.equals(bean.getId())) {
		out.println("<form method=\"post\" action=\""+ ServiceConst.SERVICE_ROOT + "/api/metadata/admin/catalog/modify/" + bean.getId() +"\">");
		out.println("<label>name: <span class=\"name\"><input type=\"text\" name=\"name\" value=\"" + bean.getName() + "\" /></span></label>");
		out.println("<label>children: <span class=\"children\"><input type=\"text\" name=\"children\" value=\"" + bean.getChildren() + "\" /></span></label>");
		out.println("<label>position: <span class=\"position\"><input type=\"text\" name=\"position\" value=\"" + bean.getPosition() + "\" /></span></label>");
		out.println("<label><input type=\"hidden\" name=\"id\" value=\"" + bean.getId() + "\"/>\n");
		out.println("<input type=\"submit\" value=\"保存\"/></label>");
		out.println("</form>");
	} else {
		out.println("<form action=\"" + ServiceConst.SERVICE_ROOT + "/admin/catalogs.jsp\">");
		out.println("<label>name: <span class=\"name\">" + bean.getName() + "</span></label>");
		out.println("<label>children: <span class=\"children\">" + bean.getChildren() + "</span></label>");
		out.println("<label>position: <span class=\"position\">" + bean.getPosition() + "</span></label>");
		out.println("<label><input type=\"hidden\" name=\"modify\" value=\"" + bean.getId() + "\"/>\n");
		out.println("<input type=\"submit\" value=\"修改\"/></label>");
		out.println("</form>");
	}
}
%>
<h5>新建分类</h5>
<form action="<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/admin/catalog/create" method="post" >
<label>name: <span class="name"><input type="text" name="name"/></span></label>
<label>children: <span class="children"><input type="text" name="children"/></span></label>
<label>position: <span class="position"><input type="text" name="position"/></span></label>
<label><input type="submit" value="创建" /></label>
</form>
<h5>删除分类</h5>
<form action="<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/admin/catalog/delete" method="post" >
<label>该分类的id:<span class="id"><input type="text" name="id"/></span></label>
<label><input type="submit" value="删除" /></label>
</form>
</body>
</html>