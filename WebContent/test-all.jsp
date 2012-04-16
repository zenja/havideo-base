<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.List,org.zenja.havideo.metadata.beans.*,org.zenja.havideo.metadata.services.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test All -- Console</title>
</head>
<body>

<% 
	VideoService videoService = new VideoService();
	CommentService commentService = new CommentService();
	List<Video> allVideos = videoService.findAllVideo();
	List<Comment> allComments = commentService.findAllComments();
%>

<h1 style="color:blue">Console</h1>

<hr />
<h2>All Videos: </h2>
<%
	for(Video v : allVideos) {
		out.print(videoService.makeJson(v) + "<br />");
	}
%>

<hr />
<h2>All Comments: </h2>
<%
	for(Comment c : allComments) {
		out.print(commentService.makeJson(c) + "<br />");
	}
%>

<hr />
<h2>Upload a video</h2>
<form action="api/video/raw/upload" enctype="multipart/form-data" method="post">
	Select a file : <input type="file" name="file" size="45" /><br />
	User_id: <input type="text" name="user_id" value="1"></input><br />
	Title: <input type="text" name="title" value="Test Title"></input><br />
	Summary: <input type="text" name="summary" value="Test Summary"></input><br />
	Tags: <input type="text" name="tags" value="狗,猫,僵尸,weibo"></input><br />
	Catalog: <input type="text" name="catalog" value="搞笑"></input><br />
	Uploader Name: <input type="text" name="uploaderName" value="wangxing"></input><br />
	<input type="submit" value="Upload It" />
</form>

<hr />
<h2>Post a comment</h2>
<form action="api/metadata/comment" method="post">
	VideoId: <input type="text" name="videoId"></input><br />
	Content: <input type="text" name="content" value="Good Video!"></input><br />
	Username: <input type="text" name="userName" value="wangxing"></input><br />
	<input type="submit" value="Post!" />
</form>

<hr />
<h2>Download any file using HDFS-style file path</h2>
<form action="api/hdfs/download" method="GET"><!-- Method: GET -->
	HDFS File Path: <input type="text" name="path"></input><br />
	<input type="submit" value="Download" />
</form>

<hr />
<h2 style="color:grey">View a thumbnail (Deprecated)</h2>
<p>
Url Style: http://localhost:9999/havideo-base/api/video/thumbnail/<span>&lt;file name&gt;</span><br />
Note that the host and port may need to be modified.<br />
You are commented to use the interface "Download any file using HDFS-style file path" instead.
</p>

<hr />
<h2 style="color:grey">Download a converted video (Deprecated)</h2>
<p>
Url Style: http://localhost:9999/havideo-base/api/video/converted/<span>&lt;file name&gt;</span><br />
Note that the host and port may need to be modified.<br />
You are commented to use the interface "Download any file using HDFS-style file path" instead.
</p>

<hr />
<h2>Find Videos' IDs by tag</h2>
<p>
Url Style: http://localhost:9999/havideo-base/api/metadata/tag/<span>&lt;tag name&gt;</span><br />
<br />
Or:
</p>
<form action="api/metadata/tag" method="GET"><!-- Method: GET -->
	Tag: <input type="text" name="tag"></input><br />
	<input type="submit" value="Find" />
</form>

<hr />
<h2>Find Videos' IDs by catalog</h2>
<p>
Url Style: http://localhost:9999/havideo-base/api/metadata/catalog/<span>&lt;tag name&gt;</span><br />
<br />
Or:
</p>
<form action="api/metadata/catalog" method="GET"><!-- Method: GET -->
	Tag: <input type="text" name="catalog"></input><br />
	<input type="submit" value="Find" />
</form>

</body>
</html>