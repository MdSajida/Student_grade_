package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
	private static final String USER = "your_mysql_username";
	private static final String PASSWORD = "your_mysql_password";


    public static Connection getConnection() {
    	try {
    	    // Register the MySQL JDBC driver
    	    Class.forName("org.mysql.jdbc.Driver");
    	    // Establish the connection
    	    return DriverManager.getConnection(URL, USER, PASSWORD);
    	} catch (ClassNotFoundException e) {
    	    throw new RuntimeException("MySQL JDBC driver not found", e);
    	} catch (SQLException e) {
    	    throw new RuntimeException("Error connecting to the database", e);
    	}

    }
}
