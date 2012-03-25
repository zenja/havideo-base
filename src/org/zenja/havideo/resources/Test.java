package org.zenja.havideo.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.hadoop.conf.Configuration;
import org.zenja.havideo.hdfs.HDFSConfiguration;
import org.zenja.havideo.hdfs.exceptions.HDFSConfigurationNotInitializedException;

@Path("/test")
public class Test {
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
}
