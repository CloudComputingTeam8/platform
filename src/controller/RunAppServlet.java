package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;

import model.DBDriver;
import platform.*;

/*
 * controller.RunAppServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * Run App servlet servlet
 * run the app which is deployed in the platform
 * the platform suports two type of app
 * the web app
 * the pure java app which need to implements the platform.AppInterface
 * 
 */

public class RunAppServlet extends HttpServlet {
	private static String successURL = "index.jsp";
	private static final String destdir = "extractorFile";
	private DBDriver driver;
	private String filePath = null;
			
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException{
		//get appID
		int appID = Integer.parseInt(req.getParameter("appID"));
		int pv = 0;
		try {
			//get some information of the app
			driver = new DBDriver();
			String query = "SELECT name, pv, status FROM appinfo WHERE appID=\""+appID+"\" ";
			ResultSet result = driver.executeQuery(query);
			if(result.next()) {
				String appName = result.getString("name");
				String status = result.getString("status");
				//if it is a pure java app
				if (status.equals("Java App")) {
					System.out.println("Running Java App");
					filePath = req.getServletContext().getRealPath("") + File.separator + destdir + File.separator + appName;
					AppInterface app = getCustomApp(appName);
					app.start();
					app.stop();
				}
				//if it is a web app
				else if(status.equals("Web App")){
					successURL = "/CloudComputingTeam8/extractorFile/"+appName;
				}				
				pv = result.getInt("pv");				
			}
			//update the page view
			pv++;
			query = "UPDATE appinfo SET pv = "+pv+" WHERE appID = "+appID;
			driver.executeAddQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//close DB connection
		finally {
			if(driver != null)
				try {
					driver.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		res.sendRedirect(successURL);
	}
	
	//code from the lecture's demo, get the java app by it's name
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
	
	//code from the lecture's demo, get class loader
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
