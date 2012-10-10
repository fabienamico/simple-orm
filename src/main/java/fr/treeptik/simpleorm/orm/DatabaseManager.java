package fr.treeptik.simpleorm.orm;

import java.sql.Connection;
import java.sql.DriverManager;

import fr.treeptik.simpleorm.exception.DBException;

public class DatabaseManager {

	private static Connection connection;
	
	public static Connection getConnection() throws DBException{
		try{
			if(connection == null || connection.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/simpleorm", "root", "");
			}
		}catch(Exception e){
			throw new DBException(e.getMessage(), e);
		}
		return connection;
	}
	
}
