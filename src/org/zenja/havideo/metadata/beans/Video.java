package org.zenja.havideo.metadata.beans;

import java.util.Set;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity
public class Video {
	@Id private ObjectId id;

	private String title;
	private String summary;
	private Set<String> tags;
	private String catalog;
	private String thumbnailPath;
	private String rawVideoPath;
	private String convertedVideoPath;
	private int viewTimes;
	private int score;
	private int scoreTimes;
	private String uploaderName;
	private long uploadTime;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getRawVideoPath() {
		return rawVideoPath;
	}

	public void setRawVideoPath(String rawVideoPath) {
		this.rawVideoPath = rawVideoPath;
	}

	public String getConvertedVideoPath() {
		return convertedVideoPath;
	}

	public void setConvertedVideoPath(String convertedVideoPath) {
		this.convertedVideoPath = convertedVideoPath;
	}

	public int getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(int viewTimes) {
		this.viewTimes = viewTimes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScoreTimes() {
		return scoreTimes;
	}

	public void setScoreTimes(int scoreTimes) {
		this.scoreTimes = scoreTimes;
	}

	public String getUploaderName() {
		return uploaderName;
	}

	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}

	public long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}
	
}
