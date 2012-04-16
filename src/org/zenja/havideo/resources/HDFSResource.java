package org.zenja.havideo.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.zenja.havideo.hdfs.HDFS;
import org.zenja.havideo.hdfs.utils.StreamingOutputMaker;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/hdfs")
public class HDFSResource {
	@GET @Path("/download")
	public Response downloadWithQueryParam(@QueryParam("path") final String path) {
		try {
			//check if params are valid
			if(path == null || path.isEmpty()) {
				return Response.status(500).entity("Parameters not valid").build();
			}
			if(HDFS.isExist(path) == false) {
				return Response.status(500).entity("File not exists.").build();
			}
			
			return Response.ok(
					StreamingOutputMaker.makeStreamingOutput(path), 
					MediaType.MULTIPART_FORM_DATA).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Exception Occured.").build();
		}
	}
	
	@GET @Path("/download/{path}")
	public Response download(@PathParam("path") final String encreptedPath) {
		String realPath = encreptedPath.replace(',', '/');
		try {
			//check if params are valid
			if(realPath == null || realPath.isEmpty()) {
				return Response.status(500).entity("Parameters not valid").build();
			}
			if(HDFS.isExist(realPath) == false) {
				return Response.status(500).entity("File not exists.").build();
			}
			
			return Response.ok(
					StreamingOutputMaker.makeStreamingOutput(realPath), 
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
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail, 
		@FormDataParam("destination") String dest) {
 
		try {
			HDFS.createFile(uploadedInputStream, dest);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Fail to create file to HDFS").build();
		}
 
		String output = "File uploaded to : " + dest;
		return Response.status(200).entity(output).build();
	}
}