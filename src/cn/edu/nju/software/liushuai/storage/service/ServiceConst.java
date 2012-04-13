package cn.edu.nju.software.liushuai.storage.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServiceConst implements ServletContextListener {
	public static String SERVICE_ROOT = "";
	// return strings
	public static final String RETURN_TEXT_PLAIN_TRUE = "true";
	public static final String RETURN_TEXT_PLAIN_FALSE = "false";
	//
	public static final int LISTING_LIMIT = 20;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// load configuration properties
		SERVICE_ROOT = event.getServletContext().getInitParameter("metadata_service_root");
		
		assert SERVICE_ROOT != null;
	}
}
