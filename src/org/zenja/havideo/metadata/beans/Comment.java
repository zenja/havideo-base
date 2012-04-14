package org.zenja.havideo.metadata.beans;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Comment {
	@Id private ObjectId id;

	private String content;
	private String userName;
	private String commentTime;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
}
