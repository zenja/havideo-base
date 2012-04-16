package org.zenja.havideo.metadata.services;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.daos.CommentDAO;
import org.zenja.havideo.metadata.daos.factories.CommentDAOFactory;

public class CommentService {
	
	private CommentDAO commentDAO = CommentDAOFactory.getDAO();

	public List<Comment> getCommentsByVideoId(String videoId) {
		return commentDAO.findByVideoId(videoId);
	}
	
	public List<Comment> findAllComments() {
		return commentDAO.findAll();
	}
	
	public String makeJsonForArray(List<Comment> comments) {
		JsonConfig jsonConfig = new JsonConfig();   
	    jsonConfig.registerJsonValueProcessor(ObjectId.class, new JsonValueProcessor() {
	        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	            return null;
	        }
	 
	        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
	            return value.toString();
	        }
	 
	    });
		JSONArray array = JSONArray.fromObject(comments, jsonConfig);
		return array.toString();
	}

	public String makeJson(Comment comment) {
		JsonConfig jsonConfig = new JsonConfig();   
	    jsonConfig.registerJsonValueProcessor(ObjectId.class, new JsonValueProcessor() {
	        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	            return null;
	        }
	 
	        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
	            return value.toString();
	        }
	 
	    });
		JSONObject o = JSONObject.fromObject(comment, jsonConfig);
		return o.toString();
	}

	public Comment createComment(String videoId, String content, String userName) {
		Comment c = new Comment();
		c.setVideoId(new ObjectId(videoId));
		c.setContent(content);
		c.setUserName(userName);
		c.setCommentTime(new Long(new Date().getTime()));
		
		return c;
	}
	
	public Comment getCommentById(String id) {
		return commentDAO.findById(id);
	}
	
	public Comment getCommentById(ObjectId id) {
		return commentDAO.findById(id);
	}
	
	public void deleteComment(Comment c) {
		commentDAO.delete(c);
	}
	
	public void deleteCommentById(String commentId) {
		Comment c = getCommentById(commentId);
		commentDAO.delete(c);
	}
	
	public void deleteCommentById(ObjectId commentId) {
		Comment c = getCommentById(commentId);
		commentDAO.delete(c);
	}

	public void saveComment(Comment comment) {
		commentDAO.save(comment);
	}
}
