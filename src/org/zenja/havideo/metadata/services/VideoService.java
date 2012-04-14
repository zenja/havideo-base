package org.zenja.havideo.metadata.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.daos.VideoDAO;
import org.zenja.havideo.metadata.daos.factories.VideoDAOFactory;

public class VideoService {
	private VideoDAO videoDAO = VideoDAOFactory.getDAO();
	
	public void saveVideo(Video video) {
		videoDAO.save(video);
	}
	
	public Video createVideo() {
		return new Video();
	}
	
	public Video createVideo(
			final String title, 
			final String summary, 
			final String tags, 
			final String catalog, 
			final String uploaderName) {
				
		Video video = createVideo();
		
		// Set some properties
		video.setTitle(title);
		video.setSummary(summary);
		video.setCatalog(catalog);
		video.setUploaderName(uploaderName);
		
		// Set empty properties
		video.setConvertedVideoPath("");
		video.setRawVideoPath("");
		video.setScore(0);
		video.setScoreTimes(0);
		video.setThumbnailPath("");
		video.setViewTimes(0);
		
		// Set tags
		Set<String> tagSet = new HashSet<String>();
		for(String tag : tags.split(",")) {
			tagSet.add(tag);
		}
		video.setTags(tagSet);
		
		// Set uploadTime
		video.setUploadTime(new Long(new Date().getTime()));
		
		return video;
	}

	public Video findById(String id) {
		return videoDAO.findById(id);
	}
	
	public List<Video> findAllVideo() {
		return videoDAO.findAll();
	}

	public void deleteAllVideos() {
		for(Video video : findAllVideo()) {
			videoDAO.delete(video);
		}
	}

	public String makeJson(Video video) {
		JsonConfig jsonConfig = new JsonConfig();   
	    jsonConfig.registerJsonValueProcessor(ObjectId.class, new JsonValueProcessor() {
	        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	            return null;
	        }
	 
	        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
	            return value.toString();
	        }
	 
	    });
		JSONObject o = JSONObject.fromObject(video, jsonConfig);
		return o.toString();
	}
}
