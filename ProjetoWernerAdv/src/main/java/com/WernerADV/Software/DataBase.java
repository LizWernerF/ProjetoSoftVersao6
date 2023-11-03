package com.WernerADV.Software;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

	  public static final Logger logger = Logger.getLogger(DataBase.class.getName());
	  public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	  public static final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:3305/clienteswerneradv";
	  public static final String DB_USER = "username1";
	  public static final String DB_PASSWORD = "LizWerner01-";
			
			private DataBase() {
				
			}
			
			public static Connection getDBConnection() throws SQLException {
				Connection connection = null;

				try {
					Class.forName(DB_DRIVER);
				} catch (ClassNotFoundException exception) {
			        logger.log(Level.SEVERE, exception.getMessage());
			        throw new SQLException("Database driver not found", exception);
			    }

				try {
					connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
					
				} catch (SQLException exception) {
			        logger.log(Level.SEVERE, exception.getMessage());
			        throw exception;
			    }

			    return connection;
			}
			
		}