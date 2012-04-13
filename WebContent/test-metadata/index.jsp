<%@page import="cn.edu.nju.software.liushuai.storage.service.ServiceConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试总入口</title>
</head>
<body>
<h1>各种测试</h1>
<h3>初始化</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/admin/init.jsp">初始化数据库</a></p>
<h3>分类管理</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/admin/catalogs.jsp">新建和修改分类</a></p>
<h3>分类列表</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/catalogs">获得所有分类信息</a></p>
<h3>视频列表</h3>
<p>(注意激活)<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/catalog/{catalog}?start=最后一个视频id&limit=个数</p>
<h3>视频上传</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/test/upload.html">上传页面</a></p>
<h3>视频获取</h3>
<p>(注意激活)<% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/view/{id}</p>
<h3>视频激活</h3>
<p><% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/activate/{id}</p>
<h3>添加评论</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/test/comment.html">上传评论</a></p>
<h3>获得评论</h3>
<p><% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/comments/{id}?start=最后一个评论id&limit=个数</p>
<h3>评分</h3>
<p><a href="<% out.print(ServiceConst.SERVICE_ROOT); %>/test/score.html">上传评论</a></p>
<h3>标签搜索</h3>
<p><% out.print(ServiceConst.SERVICE_ROOT); %>/api/metadata/user/search/tags?find=标签(空格隔开)&start=最后一个评论id&limit=个数</p>
</body>
</html>