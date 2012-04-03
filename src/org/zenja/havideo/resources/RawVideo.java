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
import org.zenja.havideo.hdfs.HDFS;
import org.zenja.havideo.hdfs.HDFSConfiguration;
import org.zenja.havideo.hdfs.utils.StreamingOutputMaker;
import org.zenja.havideo.resources.utils.FileNameGenerator;
import org.zenja.havideo.resources.utils.ParameterChecker;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/video/raw")
public class RawVideo {
	
	private static Logger logger = Logger.getLogger(RawVideo.class);
	private String rawVideoDirectory = HDFSConfiguration.getRawVideoDirectory();
	
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
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		//check if all params are available
		if(uploadedInputStream == null) {
			logger.debug("In method RawVideo.uploadFile: params not available");
			
			return Response.status(500).entity("Parameters not available").build();
		}
		
		//TODO: check if the user exists (Urgent!)
		
		//build target directory path
		String generatedFileName = 
				FileNameGenerator.generateVideoFileName(userId, fileDetail.getFileName());
		String filePath = rawVideoDirectory + "/" + generatedFileName;
		
		//TODO notify video converter service!! (Urgent!)
		
		//TODO notify video meta-data service!! (Urgent!)
		
		try {
			HDFS.createFile(uploadedInputStream, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			
			logger.debug("File upload " + filePath + " fail!");
			
			return Response.status(500).entity("Fail to upload file to " + filePath).build();
		}
 
		String output = "File uploaded to : " + filePath;
		
		logger.debug("File upload " + filePath + " success.");
		
		return Response.status(200).entity(output).build();
	}
}
