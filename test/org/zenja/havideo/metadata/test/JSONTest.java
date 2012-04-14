package org.zenja.havideo.metadata.test;

import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.services.VideoService;

public class JSONTest {
	
	public static void main(String[] args) {
//		List<String> list = new ArrayList<String>();  
//		list.add("first"); 
//		list.add("second");
//		JSONArray jsonArray = JSONArray.fromObject( list );  
//		System.out.println(jsonArray);
		
		VideoService service = new VideoService();
		Video video = service.createVideo(
				"test title", 
				"test summary", 
				"test tag1,test tag2", 
				"test catalog", 
				"test uploaderName");
		service.saveVideo(video);
		System.out.println(service.makeJson(video));
	}
}
