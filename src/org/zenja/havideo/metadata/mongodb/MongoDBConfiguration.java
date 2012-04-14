package org.zenja.havideo.metadata.mongodb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MongoDBConfiguration implements ServletContextListener {
	public static final String DB_NAME = "havideo_metadata";
	
	private static String host;
	
	public static String getHost() {
		return host;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		host = event.getServletContext().getInitParameter("mongodb_connection_host");
		assert host != null;
	}
	
	
}
