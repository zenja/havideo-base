package org.zenja.havideo.metadata.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;

import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.services.TagService;

@Path("/tag")
public class TagResource {
	
	private TagService tagService = new TagService();
	
	@GET
	@Path("/{tag}")
	public Response getAllVideosByTag(@PathParam("tag") final String tag) {
		List<Video> videos = tagService.getAllVideosByTag(tag);
		List<String> videoIds = new ArrayList<String>();
		for(Video v : videos) {
			videoIds.add(v.getId().toString());
		}
		JSONArray json = JSONArray.fromObject(videoIds);
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	public Response getAllVideosByTagWithQueryParam(@QueryParam("tag") final String tag) {
		List<Video> videos = tagService.getAllVideosByTag(tag);
		List<String> videoIds = new ArrayList<String>();
		for(Video v : videos) {
			videoIds.add(v.getId().toString());
		}
		JSONArray json = JSONArray.fromObject(videoIds);
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
