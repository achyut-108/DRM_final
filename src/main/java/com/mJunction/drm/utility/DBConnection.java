package com.mJunction.drm.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {


    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);

	public Connection dbConnection() {
		Properties prop = new Properties();
		InputStream input = null;
		Connection connection = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
//			input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
			input = classLoader.getResourceAsStream("config.properties");

			prop.load(input);

			String locDbName = prop.getProperty("database");
			String locDbUser = prop.getProperty("dbuser");
			String locdbPassword = prop.getProperty("dbpassword");
			String jdbcDriver = prop.getProperty("driver");

			LOGGER.info("[dbConnection] : jdbcDriver : " + jdbcDriver);

			try {
				
				String url = locDbName;
				String user = locDbUser;
				String pass = locdbPassword;


                LOGGER.info("[dbConnection] : " + Class.forName(jdbcDriver).newInstance());

				Class.forName(jdbcDriver).newInstance();



				connection = DriverManager.getConnection(url, user, pass);

				LOGGER.info("[dbConnection] : connection : " + connection);

			} catch (Exception ex) {
				LOGGER.error("[dbConnection] : exception : ",ex);
			}
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return connection;

	}
}
