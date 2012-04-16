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
import org.zenja.havideo.metadata.services.CatalogService;
import org.zenja.havideo.metadata.services.VideoService;

@Path("/catalog")
public class CatalogResource {
	CatalogService catalogService = new CatalogService();
	VideoService videoService = new VideoService();
	
	@GET
	@Path("/{catalog}")
	public Response getAllVideosByCatalog(@PathParam("catalog") final String catalog) {
		List<Video> videos = catalogService.getAllVideosByCatalog(catalog);
		List<String> videoIds = new ArrayList<String>();
		for(Video v : videos) {
			videoIds.add(v.getId().toString());
		}
		JSONArray json = JSONArray.fromObject(videoIds);
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	public Response getAllVideosByCatalogWithQueryParam(@QueryParam("catalog") final String catalog) {
		List<Video> videos = catalogService.getAllVideosByCatalog(catalog);
		List<String> videoIds = new ArrayList<String>();
		for(Video v : videos) {
			videoIds.add(v.getId().toString());
		}
		JSONArray json = JSONArray.fromObject(videoIds);
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
