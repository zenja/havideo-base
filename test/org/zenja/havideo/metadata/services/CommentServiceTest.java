package org.zenja.havideo.metadata.services;

import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.beans.Video;

public class CommentServiceTest {
	private static CommentService commentService = new CommentService();
	private static VideoService videoService = new VideoService();
	
	public static void main(String[] args) {
		
		Video v = videoService.createVideo();
		videoService.saveVideo(v);
		
		Comment c1 = commentService.createComment(v.getId().toString(), "Good Video!", "zenja");
		Comment c2 = commentService.createComment(v.getId().toString(), "Excellent Video!", "zenja");
		Comment c3 = commentService.createComment(v.getId().toString(), "Great Video!", "zenja");
		
		commentService.saveComment(c1);
		commentService.saveComment(c2);
		commentService.saveComment(c3);
		
		String json = commentService.makeJsonForArray(commentService.getCommentsByVideoId(v.getId().toString()));
		System.out.println(json);
		
		videoService.deleteVideo(v);
		commentService.deleteComment(c1);
		commentService.deleteComment(c2);
		commentService.deleteComment(c3);
	}
}
