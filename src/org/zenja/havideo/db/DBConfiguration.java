package org.zenja.havideo.db;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class DBConfiguration  implements ServletContextListener  {
	private static Logger logger = Logger.getLogger(DBConfiguration.class);
	protected static String username;
	protected static String password;
	protected static String url;
	protected static String driver;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// load configuration properties
		username = event.getServletContext().getInitParameter("db_username");
		password = event.getServletContext().getInitParameter("db_password");
		url = event.getServletContext().getInitParameter("db_url");
		driver = event.getServletContext().getInitParameter("db_driver");
		
		logger.debug("DB configurations:");
		logger.debug("db_username: " + username);
		logger.debug("db_password: " + password);
		logger.debug("db_url: " + url);
		logger.debug("db_driver: " + driver);
		
		assert username != null;
		assert password != null;
		assert url != null;
		assert driver != null;
	}
	
	public static String getUsername() {
		assert username != null;
		return username;
	}
	
	public static String getPassword() {
		assert password != null;
		return password;
	}

	public static String getUrl() {
		assert url != null;
		return url;
	}

	public static String getDriver() {
		assert driver != null;
		return driver;
	}

}
