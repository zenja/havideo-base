package org.zenja.havideo.test.hdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSTest {

	private static Configuration conf = new Configuration();
	
	static {
		conf.addResource(new Path("/home/wangxing/Software/hadoop-1.0.1/conf/core-site.xml"));
	    conf.addResource(new Path("/home/wangxing/Software/hadoop-1.0.1/conf/hdfs-site.xml"));
	}
	
	public static void main(String[] args) {
		HDFSTest test = new HDFSTest();
		System.out.println(conf.toString());
		test.testCreateFile();
		test.testDeleteFile();
	}
	
	public void testCreateFile() {
		try {
		    FileSystem fileSystem = FileSystem.get(conf);
		    
		    //print fs uri
		    System.out.println("fs uri: " + fileSystem.getUri());
		    
		    // Check if the file already exists
		    Path path = new Path("/tmp/test.jpg");
		    if (fileSystem.exists(path)) {
		        System.out.println("File " + path.getName() + " already exists");
		        return;
		    }

		    // Create a new file and write data to it.
		    FSDataOutputStream out = fileSystem.create(path);
		    InputStream in = new BufferedInputStream(new FileInputStream(
		        new File("/home/wangxing/temp/test.jpg")));

		    byte[] b = new byte[1024];
		    int numBytes = 0;
		    while ((numBytes = in.read(b)) > 0) {
		        out.write(b, 0, numBytes);
		    }

		    // Close all the file descriptors
		    in.close();
		    out.close();
		    fileSystem.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteFile() {
		try {
			FileSystem fileSystem = FileSystem.get(conf);
			fileSystem.delete(new Path("/tmp/test.jpg"), false);
			fileSystem.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
