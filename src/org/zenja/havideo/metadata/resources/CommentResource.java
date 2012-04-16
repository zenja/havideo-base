package org.zenja.havideo.metadata.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.services.CommentService;
import org.zenja.havideo.metadata.services.VideoService;

@Path("/comment")
public class CommentResource {
	
	CommentService commentService = new CommentService();
	VideoService videoService = new VideoService();
	
	@GET
	@Path("/{video_id}")
	public Response getComment(@PathParam("video_id") final String videoId) {
		List<Comment> comments = commentService.getCommentsByVideoId(videoId);
		return Response.ok(commentService.makeJsonForArray(comments), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	public Response postComment(
			@FormParam("videoId") final String videoId, 
			@FormParam("content") final String content, 
			@FormParam("userName") final String userName) {
		
		// check if params are valid
		if(videoId == null || content == null || userName == null 
				|| videoId.isEmpty() || content.isEmpty() || userName.isEmpty()) {
			return Response.status(500).entity("Parameters not valid").build();
		}
		if(videoService.isVideoIdExists(videoId) == false) {
			return Response.status(500).entity("videoId not exists").build();
		}
		
		Comment comment = commentService.createComment(videoId, content, userName);
		commentService.saveComment(comment);
		
		return Response.ok().entity("Comment is created successfully.").build();
	}
	
	
}
