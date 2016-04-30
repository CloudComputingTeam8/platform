package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBDriver;
import platform.AppInterface;
import platform.AppLoader;
import platform.EmptyApp;

public class RunAppServlet extends HttpServlet {
	private static final String destdir = "extractorFile";
	private DBDriver driver;
	private String filePath = null;
			
	public void doGet(HttpServletRequest req,HttpServletResponse res){
		
		int appID = Integer.parseInt(req.getParameter("appID"));
		int pv = 0;
		try {
			driver = new DBDriver();
			String query = "SELECT name, pv FROM appinfo WHERE appID=\""+appID+"\" ";
			ResultSet result = driver.executeQuery(query);
			if(result.next()) {
				String appName = result.getString("name");
				filePath = req.getServletContext().getRealPath("") + File.separator + destdir;
				AppInterface app = getCustomApp(appName);
				pv = result.getInt("pv");
				app.start();
				app.stop();
			}
			pv++;
			query = "UPDATE appinfo SET pv = "+pv+" WHERE appID = "+appID;
			driver.executeAddQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(driver != null)
				try {
					driver.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	private AppInterface getCustomApp(String appName) {
		ClassLoader loader = getClassLoader(appName);
		try {
			Class<?> appClass = loader.loadClass(appName);  // load the app class
			return (AppInterface) appClass.newInstance();  // create app instance
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Could not find app class called: " + appName);
		}
		catch (InstantiationException | IllegalAccessException ex) {
			System.out.println("Could not create an instance of: " + appName);
		}
		return new EmptyApp();  // return a default EmptyApp.
	}
	
	private ClassLoader getClassLoader(String appName) {
		ClassLoader delegate = getClass().getClassLoader(); // my ClassLoader
		System.out.println("filepath: "+filePath);
		File directory = new File(filePath);
		try {
			URL[] urls = { directory.toURI().toURL() }; // convert to URL array
			return new AppLoader(appName, urls, delegate);
		} 
		catch (MalformedURLException e) {
			System.out.println("Could not create a URLClassLoader with bad URL");
			return delegate;  // will attempt to load with delegate instead
		}
	}

}
