package org.zenja.havideo.hdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

public class HDFS implements ServletContextListener {
	private static Logger logger = Logger.getLogger(HDFS.class);
	private static FileSystem fileSystem;
	
	static void initFileSystem(FileSystem fs) {
		logger.debug("About to initializing the filesystem...");
		
		fileSystem = fs;
		
		logger.debug("Filesystem initialized. URI: " + fileSystem.getUri().toString());
	}
	
	public static void createFile(String source, String dest) throws IOException {
		assert fileSystem != null;
		
		
	    InputStream in = new BufferedInputStream(new FileInputStream(
	        new File(source)));
	    
	    createFile(in, dest);
	    
	    //close the stream
	    in.close();
	}
	
	public static void createFile(InputStream sourceInputStream, String dest) throws IOException {
		assert fileSystem != null;
		
	    // Check if the file already exists
	    Path path = new Path(dest);
	    if (fileSystem.exists(path)) {
	        System.out.println("File " + path.getName() + " already exists");
	        return;
	    }
	    
		// Create a new file and write data to it.
	    FSDataOutputStream out = fileSystem.create(path);
	    
	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    while ((numBytes = sourceInputStream.read(b)) > 0) {
	        out.write(b, 0, numBytes);
	    }

	    // Close output stream
	    out.close();
	    
	}
	
	public static void deleteFile(String source, boolean isRecursive) throws IOException {
		assert fileSystem != null;
		
		fileSystem.delete(new Path(source), isRecursive);
	}
	
	public static FSDataInputStream getFileAsStream(String source) throws IOException {
		assert fileSystem != null;
		

	    Path path = new Path(source);
	    if (!fileSystem.exists(path)) {
	        throw new IOException("File not exist: " + source);
	    }

	    FSDataInputStream in = fileSystem.open(path);

	    return in;
	}
	
	public static boolean isExist(String source) throws IOException {
		return fileSystem.exists(new Path(source));
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// close HDFS connection when the web context is to be destroyed
		try {
			fileSystem.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// do nothing
	}
}
