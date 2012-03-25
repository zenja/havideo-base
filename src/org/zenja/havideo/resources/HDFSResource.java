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

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/hdfs")
public class HDFSResource {
	@GET @Path("/download/{path}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public StreamingOutput download(@PathParam("path") final String encreptedPath) {
		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException, WebApplicationException {
				String realPath = encreptedPath.replace(',', '/');
				FSDataInputStream in = HDFS.getFileAsStream(realPath);
				byte[] b = new byte[1024];
			    int numBytes = 0;
			    while ((numBytes = in.read(b)) > 0) {
			    	output.write(b, 0, numBytes);
			    }
			    output.close();
			}
		};
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