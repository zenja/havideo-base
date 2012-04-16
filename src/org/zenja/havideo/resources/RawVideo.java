package org.zenja.havideo.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.zenja.havideo.converter.ConverterDriver;
import org.zenja.havideo.hdfs.HDFS;
import org.zenja.havideo.hdfs.HDFSConfiguration;
import org.zenja.havideo.hdfs.utils.StreamingOutputMaker;
import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.services.VideoService;
import org.zenja.havideo.resources.utils.FileNameGenerator;
import org.zenja.havideo.resources.utils.ParameterChecker;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/video/raw")
public class RawVideo {
	
	private static Logger logger = Logger.getLogger(RawVideo.class);
	private String rawVideoDirectory = HDFSConfiguration.getRawVideoDirectory();
	private String convertedVideoDirectory = HDFSConfiguration.getConvertedVideoDirectory();
	private String thumbnailDirectory = HDFSConfiguration.getVideoThumbnailDirectory();
	private VideoService videoService = new VideoService();
	
	@GET 
	@Path("/{file_name}")
	public Response download(
			@PathParam("file_name") final String fileName) {
		try {
			//build whole path of the file
			String filePath = rawVideoDirectory + "/" + fileName;
			
			/*
			 * Check if params are valid
			 */
			if(HDFS.isExist(filePath) == false || 
					ParameterChecker.checkFileName(fileName) == false) {
				
				logger.debug("Path not exists or invalid user_id or file_name: ");
				logger.debug("--Path: " + filePath);
				logger.debug("--file_name: " + fileName);
				
				return Response.status(500).entity("Parameters not valid").build();
			}
			
			return Response.ok(
					StreamingOutputMaker.makeStreamingOutput(filePath), 
					MediaType.MULTIPART_FORM_DATA).build();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Exception Occured.").build();
		}
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFile(
		@FormDataParam("user_id") int userId,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail, 
		@FormDataParam("title") final String title, 
		@FormDataParam("summary") final String summary, 
		@FormDataParam("tags") final String tags, 
		@FormDataParam("catalog") final String catalog, 
		@FormDataParam("uploaderName") final String uploaderName) {
 
		//check if all params are available
		if(uploadedInputStream == null || fileDetail == null) {
			logger.debug("In method RawVideo.uploadFile: params not available");
			
			return Response.status(500).entity("Parameters not available").build();
		}
		
		//TODO: check if the file is a video file
		
		//TODO: check if the user exists (Urgent!)
		
		//build target directory path
		String generatedFileName = 
				FileNameGenerator.generateVideoFileName(userId, fileDetail.getFileName());
		String filePath = rawVideoDirectory + "/" + generatedFileName;
		
		try {
			HDFS.createFile(uploadedInputStream, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			
			logger.debug("File upload " + filePath + " fail!");
			
			return Response.status(500).entity("Fail to upload file to " + filePath).build();
		}
 
		String output = "File uploaded to : " + filePath;
		
		logger.debug("File upload " + filePath + " success.");
		
		//notify video converter service
		String srcPath = filePath;
		String flvFileName = generatedFileName.split("\\.")[0] + ".flv";
		String thumbFileName = generatedFileName.split("\\.")[0] + ".jpg";
		String videoDstPath = convertedVideoDirectory + "/" + flvFileName;
		String thumbDstPath = thumbnailDirectory + "/" + thumbFileName;
		logger.debug("Start convertion...");
		logger.debug("Source: " + srcPath);
		logger.debug("Video Destination: " + videoDstPath);
		new ConverterDriver().startConversion(srcPath, videoDstPath, thumbDstPath);
		
		// Create video object and save
		Video video = videoService.createVideo(title, summary, tags, catalog, uploaderName);
		video.setRawVideoPath(srcPath);
		video.setConvertedVideoPath(videoDstPath);
		video.setThumbnailPath(thumbDstPath);
		videoService.saveVideo(video);
		
		return Response.status(200).entity(output).build();
	}
}
