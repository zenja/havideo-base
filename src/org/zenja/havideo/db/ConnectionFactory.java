package org.zenja.havideo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection makeConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String url = DBConfiguration.getUrl();
		String username = DBConfiguration.getUsername();
		String password = DBConfiguration.getPassword();
		String driver = DBConfiguration.getDriver();
		
		Class.forName(driver).newInstance();
        return DriverManager.getConnection(url, username, password);
	}
}
