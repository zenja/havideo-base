package org.zenja.havideo.metadata.beans;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Comment {
	@Id private ObjectId id;

	private ObjectId videoId;
	private String content;
	private String userName;
	private Long commentTime;

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

	public Long getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Long commentTime) {
		this.commentTime = commentTime;
	}

	public ObjectId getVideoId() {
		return videoId;
	}

	public void setVideoId(ObjectId videoId) {
		this.videoId = videoId;
	}
}
