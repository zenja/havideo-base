package org.zenja.havideo.metadata.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.services.VideoService;

@Path("/video")
public class VideoResource {
	
	VideoService videoService = new VideoService();
	
	@GET
	@Path("/{id}")
	public Response getVideoInfo(@PathParam("id") final String id) {
		Video video = videoService.findById(id);
		return Response.ok(
				videoService.makeJson(video), 
				MediaType.APPLICATION_JSON)
				.build();
	}
	
	/**
	 * Upload the meta-data for the newly uploaded video
	 * 
	 * NO NOT NEDDED NOW:
	 * already integrated into raw video web service
	 * 
	 * @param title
	 * @param summary
	 * @param tags
	 * @param catalog
	 * @param uploaderName
	 * @return
	 */
	@POST
	@Path("/upload")
	public Response uploadVideoMetadata(
			@FormParam("title") final String title, 
			@FormParam("summary") final String summary, 
			@FormParam("tags") final String tags, 
			@FormParam("catalog") final String catalog, 
			@FormParam("uploaderName") final String uploaderName) {
		
		// check if params are valid
		if (title == null || summary == null || tags == null 
				|| catalog == null || uploaderName == null) {
			return Response.status(500).entity("Parameters not valid").build();
		}
		if (title.isEmpty() || summary.isEmpty() || tags.isEmpty()
				|| catalog.isEmpty() || uploaderName.isEmpty()) {
			return Response.status(500).entity("Parameters not valid").build();
		}
		
		Video video = videoService.createVideo(title, summary, tags, catalog, uploaderName);
		videoService.saveVideo(video);
		
		return Response.ok().entity("Video metadata is created successfully.").build();
	}
}
