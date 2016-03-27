package controller;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
	private static String successURL = "index.jsp";
	private String errorMessage;
	private DBDriver driver;
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {		
		String username = req.getParameter("userName");
		String password = req.getParameter("password");
		try {
			driver = new DBDriver();
			HttpSession session = req.getSession();
			UserBean user = getUser(username,password);
			if(user==null){
				req.setAttribute("error", errorMessage);
				RequestDispatcher rd = req.getRequestDispatcher("try.jsp");
				rd.forward(req, res);				
			}
			else{				
				List<AppBean> allApps = getAppOverview();
				List<AppBean> purchasedApps = getPurchasedApps(user,allApps);
				user.addPurchasedApps(purchasedApps);
				List<AppDetailBean> myApps = getMyApp(user,allApps);
				user.addMyApps(myApps);
				session.setAttribute("user", user);
				session.setAttribute("appOverview", allApps);
				res.sendRedirect(successURL);
			}
		}
		catch (SQLException e) {
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
	
	public UserBean getUser(String username, String password) throws SQLException{
		UserBean user = null;
		String query = "SELECT password,userID FROM userinfo WHERE username=\""+username+"\" ";
		ResultSet result = driver.executeQuery(query);
		if(!result.next())
			errorMessage = "Unknown user";
		else if(!result.getString("password").equals(password))
			errorMessage = "Incorrect password";
		else {
			int authorisation = 1;
			int userID = result.getInt("userID");
			user = new UserBean(username,authorisation,userID);
		}		
		return user;
	}
	
	public List<AppBean> getAppOverview() throws SQLException{
		List<AppBean> apps = new ArrayList<AppBean>();
		String query = "SELECT userinfo.username, appinfo.name, appinfo.price, appinfo.appID FROM userinfo, appinfo "
				+ "WHERE userinfo.userID = appinfo.owned";
		ResultSet result = driver.executeQuery(query);
		while(result.next()){
			 String name = result.getString("appinfo.name");
			 String owned = result.getString("userinfo.username");
			 int price = result.getInt("appinfo.price");
			 int appID = result.getInt("appinfo.appID");
			 AppBean app = new AppBean(name,owned,price,appID);
			 apps.add(app);
		}
		
		return apps;
	}
	
	public List<AppBean> getPurchasedApps(UserBean user,List<AppBean> allApps) throws SQLException{
		List<AppBean> purchasedApps = new ArrayList<AppBean>();
		String query = "SELECT appID FROM appauth WHERE userID= "+user.getUserID();
		ResultSet result = driver.executeQuery(query);
		while(result.next()){
			 int appID = result.getInt("appID");
			 for (AppBean app:allApps){
				 if(app.getAppID()==appID){
					 app.setAuthorisation(true);
					 purchasedApps.add(app);
				 }
			 }
		}		
		return purchasedApps;
	}
	
	public List<AppDetailBean> getMyApp(UserBean user,List<AppBean> allApps) throws SQLException{
		List<AppDetailBean> apps = new ArrayList<AppDetailBean>();
		String query = "SELECT *  FROM appinfo WHERE owned = "+user.getUserID();
		ResultSet result = driver.executeQuery(query);
		while(result.next()){
			 String name = result.getString("name");
			 int price = result.getInt("price");
			 int appID = result.getInt("appID");
			 String status = result.getString("status");
			 int pv = result.getInt("pv");
			 int income = result.getInt("income");
			 AppDetailBean app = new AppDetailBean(name,user.getUsername(),price,appID,status,pv,income);
			 apps.add(app);
			 
			 for (AppBean a:allApps){
				 if(a.getAppID()==appID)
					 a.setAuthorisation(true);
			 }
		}
		return apps;
	}
}
