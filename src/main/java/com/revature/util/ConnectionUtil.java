package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;



public class ConnectionUtil {
	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.warn("Exception thrown adding oracle driver.", e);
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url= "jdbc:oracle:thin:@myrevaturerds.cbdkc3trfzgz.us-east-2.rds.amazonaws.com:1521:ORCL";
		String username= "ERS_DB";
		String password= "p4ssw0rd";
		
		return DriverManager.getConnection(url,username,password);
	}
	public static void main(String[] args) {
		try(Connection connection = ConnectionUtil.getConnection()){
			logger.info("Connection Successful");
		}catch (SQLException e) {
			logger.error("Connection Failed", e);
		}
	}

}

