package org.zenja.havideo.hdfs;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.zenja.havideo.hdfs.exceptions.HDFSConfigurationNotInitializedException;

public class HDFSConfiguration implements ServletContextListener {
	protected static String hadoopBase;
	protected static String hadoopConfFiles;
	protected static Configuration conf;
	protected static String rawVideoDirectory;
	
	public static Configuration getConfiguration() throws HDFSConfigurationNotInitializedException {
		if(conf == null) initConf();
		
		return conf;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// load configuration files
		hadoopBase = event.getServletContext().getInitParameter("hadoop_base");
		hadoopConfFiles = event.getServletContext().getInitParameter("hadoop_conf_files");
		rawVideoDirectory = event.getServletContext().getInitParameter("hdfs_raw_video_directory");
		
		// init HDFS file system class
		try {
			HDFS.initFileSystem(FileSystem.get(HDFSConfiguration.getConfiguration()));
		} catch (HDFSConfigurationNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initConf() throws HDFSConfigurationNotInitializedException {
		if(hadoopBase == null || hadoopConfFiles == null) {
			throw new HDFSConfigurationNotInitializedException();
		}
		
		conf = new Configuration();
		for(String filename : hadoopConfFiles.split(",")) {
			String confFile = hadoopBase + "/conf/" + filename;
			conf.addResource(new Path(confFile));
			
			//DEBUG
			System.out.println("Added configuration file: " + confFile);
		}
	}
}
