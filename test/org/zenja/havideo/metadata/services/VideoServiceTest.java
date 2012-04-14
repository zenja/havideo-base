package org.zenja.havideo.metadata.services;

import org.zenja.havideo.metadata.beans.Video;

public class VideoServiceTest {

	public static void main(String[] args) {
		VideoService service = new VideoService();
		Video video = service.createVideo(
				"test title", 
				"test summary", 
				"test tag1,test tag2", 
				"test catalog", 
				"test uploaderName");
		service.saveVideo(video);
		System.out.println(service.findAllVideo().toString());
		//service.deleteAllVideos();
	}

}
