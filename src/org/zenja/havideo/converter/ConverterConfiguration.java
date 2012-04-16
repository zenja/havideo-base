package org.zenja.havideo.converter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConverterConfiguration implements ServletContextListener {
	
	private static String hadoopExecutablePath;
	private static String converterJarPath;
	private static String converterLocalDirectory;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		hadoopExecutablePath = event.getServletContext().getInitParameter("hadoop_base") + "/bin/hadoop";
		converterJarPath = event.getServletContext().getInitParameter("converter_jar_path");
		converterLocalDirectory = event.getServletContext().getInitParameter("converter_local_directory");
	}

	public static String getHadoopExecutablePath() {
		return hadoopExecutablePath;
	}

	public static String getConverterJarPath() {
		return converterJarPath;
	}

	public static String getConverterLocalDirectory() {
		return converterLocalDirectory;
	}

}
