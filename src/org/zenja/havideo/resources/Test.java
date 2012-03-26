package org.zenja.havideo.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.zenja.havideo.hdfs.HDFSConfiguration;
import org.zenja.havideo.hdfs.exceptions.HDFSConfigurationNotInitializedException;

@Path("/test")
public class Test {
	
	private static Logger logger = Logger.getLogger(Test.class);
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		Configuration conf = null;
		try {
			conf = HDFSConfiguration.getConfiguration();
		} catch (HDFSConfigurationNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(conf != null) {
			return "conf is created: " + conf.toString();
		} else {
			return "conf is null...";
		}
	}
	
	@GET
	@Path("log4j")
	@Produces(MediaType.TEXT_PLAIN)
	public String testLog4j() {
		logger.debug("this is a sample log message.");
		return "Check the console.";
	}
}
