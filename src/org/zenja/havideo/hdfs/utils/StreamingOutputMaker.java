package org.zenja.havideo.hdfs.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.log4j.Logger;
import org.zenja.havideo.hdfs.HDFS;

/**
 * Making StreamingOutput object to transfer file from HDFS
 * @author wangxing
 *
 */
public class StreamingOutputMaker {
	
	private static Logger logger = Logger.getLogger(StreamingOutputMaker.class);
	
	public static StreamingOutput makeStreamingOutput(final String path) {
		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException, WebApplicationException {
				FSDataInputStream in = HDFS.getFileAsStream(path);
				
				logger.debug("Start downloading " + path);
				
				byte[] b = new byte[10240];
			    int numBytes = 0;
			    while ((numBytes = in.read(b)) > 0) {
			    	System.out.println("Writing " + numBytes + " bytes..");
			    	output.write(b, 0, numBytes);
			    }
			    System.out.println("About to close the stream...");
			    output.close();
			    
			    logger.debug(path + " downloaded");
			}
		};
	}
}
