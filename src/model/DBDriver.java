package model;

import java.sql.*;
import java.util.Enumeration;

public class DBDriver {
	private static String urlDB = "jdbc:mysql://localhost:3306/new_schema",
			userDB = "root",
			passwordDB = "11111111";
	private Connection con;
	private Statement stmt;
	private  ResultSet res;
	
	public DBDriver() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(urlDB, userDB, passwordDB);
			stmt = con.createStatement();
		}
		catch (SQLException ex) {
			 ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query) {
		try {			
			res = stmt.executeQuery(query);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return res;
	}
	
	public void executeAddQuery(String query) {
		try {			
			stmt.execute(query);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void close() throws SQLException{
		try {
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}	
	}
	
	 public static void main(String[] args) throws Exception {
		 System.out.println("\nDrivers loaded as properties:");
		 System.out.println(System.getProperty("jdbc.drivers"));
		 System.out.println("\nDrivers loaded by DriverManager:");
		 Enumeration<Driver> list = DriverManager.getDrivers();
		 while (list.hasMoreElements())
		 System.out.println(list.nextElement());
		}
}
