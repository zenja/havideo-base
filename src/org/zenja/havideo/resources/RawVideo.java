package org.zenja.havideo.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.hadoop.fs.FSDataInputStream;
import org.zenja.havideo.hdfs.HDFS;
import org.zenja.havideo.hdfs.HDFSConfiguration;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/video/raw")
public class RawVideo {
	
	private String rawVideoDirectory = HDFSConfiguration.getRawVideoDirectory();
	
	@GET 
	@Path("/{user_id}/{file_name}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public StreamingOutput download(
			@PathParam("user_id") final String userId, 
			@PathParam("file_name") final String fileName) {
		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException, WebApplicationException {
				//build whole path of the file
				String filePath = rawVideoDirectory + "/" + userId + "/" + fileName;
				
				//check if the file exists first and user_id and file_name
				if(HDFS.isExist(filePath) == false || 
						checkUserId(userId) == false || 
						checkFileName(fileName) == false) {
					
					//DEBUG
					System.out.println("Path not exists or invalid user_id or file_name: ");
					System.out.println("--Path: " + filePath);
					System.out.println("--user_id: " + userId);
					System.out.println("--file_name: " + fileName);
					return;
				}
				
				/*
				 * read the file
				 */
				FSDataInputStream in = HDFS.getFileAsStream(filePath);
				//DEBUG
				System.out.println("Start downloading " + filePath);
				byte[] b = new byte[10240];
			    int numBytes = 0;
			    while ((numBytes = in.read(b)) > 0) {
			    	System.out.println("Writing " + numBytes + " bytes..");
			    	output.write(b, 0, numBytes);
			    }
			    System.out.println("About to close the stream...");
			    output.close();
			    //DEBUG
				System.out.println(filePath + " downloaded");
			}

		};
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFile(
		@FormDataParam("user_id") String userId, 
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		//check if all params are available
		if(userId == null || uploadedInputStream == null) {
			//DEBUG
			System.out.println("In method RawVideo.uploadFile: params not available");
			return Response.status(500).entity("Parameters not available").build();
		}
		
		//build target directory path
		String directoryPath = rawVideoDirectory + "/" + userId;
		String filePath = directoryPath + "/" + fileDetail.getFileName();
		
		//TODO notify video meta-data service!! (Urgent!)
		
		try {
			HDFS.createFile(uploadedInputStream, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			//DEBUG
			System.out.println("File upload " + filePath + " fail!");
			return Response.status(500).entity("Fail to upload file to " + filePath).build();
		}
 
		String output = "File uploaded to : " + filePath;
		//DEBUG
		System.out.println("File upload " + filePath + " success.");
		return Response.status(200).entity(output).build();
	}
	
	private boolean checkFileName(String fileName) {
		// TODO fill this method
		return true;
	}

	private boolean checkUserId(String userId) {
		// TODO fill this method
		return true;
	}
}
