package hadooper.servlet;

import java.sql.*;

public class Jdbc2HiveUtil {
public static final String URL = "jdbc:hive2://localhost:10000/job";
public static final String USER = "hive";
public static final String PASSWORD = "hive";
private static Connection conn;
	public Jdbc2HiveUtil() throws Exception {
	}
	public static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}
}
