package controller;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

import javax.servlet.*;
import javax.servlet.http.*;

/*
 * controller.LoginServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * login servlet
 * handle the login in  action, retrieve relevant information of the user and set to session
 * if success, goes to dashboard
 * if not, go back to login page with error message
 * 
 */

public class LoginServlet extends HttpServlet {
	private static String successURL = "index.jsp";
	private static String adminURL = "admin.jsp";
	private String errorMessage;
	private DBDriver driver;
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		try {
			driver = new DBDriver();
			HttpSession session = req.getSession();
			UserBean user = getUser(username,password);
			//if the user does not exists
			if(user==null){
				req.setAttribute("error", errorMessage);
				RequestDispatcher rd = req.getRequestDispatcher("LogIn.jsp");
				rd.forward(req, res);
			}
			//if the user is admin
			else if (user.getAuthorisation()==3){
				List<AppBean> allApps = getAppOverview();
				List<TransactionBean> transactions = getAdminTransaction(user,allApps);
				user.setTransactions(transactions);
				session.setAttribute("user", user);
				session.setAttribute("appOverview", allApps);
				res.sendRedirect(adminURL);
			}
			//otherwise
			else{				
				List<AppBean> allApps = getAppOverview();
				//get the top 5 most popular apps
				List<AppBean> topApps = new ArrayList<AppBean>();
				int i=0,j=0;
				while(j<5&&i<allApps.size()){
					AppBean app = allApps.get(i);
					if(!app.getStatus().equals("Delete")){
						topApps.add(app);
						j++;
					}
					i++;
				}
				//get purchased apps
				List<AppBean> purchasedApps = getPurchasedApps(user,allApps);
				user.setPurchasedApps(purchasedApps);
				//get owned apps if it is developer
				if(user.getAuthorisation()==2){
					List<AppDetailBean> myApps = getMyApp(user,allApps);
					user.setMyApps(myApps);
				}				
				List<TransactionBean> transactions = getTransaction(user,allApps);
				user.setTransactions(transactions);
				
				//set session
				session.setAttribute("user", user);
				session.setAttribute("appOverview", allApps);
				session.setAttribute("topApp", topApps);
				res.sendRedirect(successURL);
			}
		}
		catch (SQLException e) {
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
	}
	
	//get the user information
	public UserBean getUser(String username, String password) throws SQLException{
		UserBean user = null;
		String query = "SELECT * FROM userinfo WHERE username=\""+username+"\" ";
		ResultSet result = driver.executeQuery(query);
		//if the user doesn't exists
		if(!result.next())
			errorMessage = "Unknown user";
		//if the password is wrong
		else if(!result.getString("password").equals(password))
			errorMessage = "Incorrect password";
		//success
		else {
			int authorisation = result.getInt("authority");
			int userID = result.getInt("userID");
			int credit = result.getInt("credit");
			user = new UserBean(username,authorisation,userID,credit);
		}		
		return user;
	}
	
	//get the general information of all apps order by page views
	public List<AppBean> getAppOverview() throws SQLException{
		List<AppBean> apps = new ArrayList<AppBean>();
		String query = "SELECT userinfo.username, appinfo.* FROM userinfo, appinfo "
				+ "WHERE userinfo.userID = appinfo.owned ORDER BY appinfo.pv DESC";
		ResultSet result = driver.executeQuery(query);
		int i =0;
		while(result.next()){
			 String name = result.getString("appinfo.name");
			 String owned = result.getString("userinfo.username");
			 String image = result.getString("appinfo.image");
			 int price = result.getInt("appinfo.price");
			 int appID = result.getInt("appinfo.appID");
			 String status = result.getString("appinfo.status");
			 String description = result.getString("appinfo.description");
			 AppBean app = new AppBean(name,owned,price,appID,image,status,description);
			 apps.add(app);				 
		}		
		return apps;
	}
	
	//get all the apps that is already bought by the user
	public List<AppBean> getPurchasedApps(UserBean user,List<AppBean> allApps) throws SQLException{
		List<AppBean> purchasedApps = new ArrayList<AppBean>();
		String query = "SELECT appID FROM appauth WHERE userID= "+user.getUserID();
		ResultSet result = driver.executeQuery(query);
		while(result.next()){
			 int appID = result.getInt("appID");
			 for (AppBean app:allApps){
				 if(app.getAppID()==appID){
					 app.setAuthorisation(true); //set authorization
					 purchasedApps.add(app);
				 }
			 }
		}		
		return purchasedApps;
	}
	
	//get all the apps that is uploaded by the user
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
			 String image = result.getString("image");
			 String description = result.getString("description");
			 AppDetailBean app = new AppDetailBean(name,user.getUsername(),price,appID,status,pv,income,image,description);
			 apps.add(app);
			 
			 for (AppBean a:allApps){
				 if(a.getAppID()==appID)
					 a.setAuthorisation(true); //the user is granted the authorization automatically if he owns it
			 }
		}
		return apps;
	}
	
	//get all the transactions of peanut bank for the user ordered by date
	public List<TransactionBean> getTransaction(UserBean user,List<AppBean> allApps)throws SQLException{
		List<TransactionBean> transactions = new ArrayList<TransactionBean>();
		String query = String.format("SELECT *  FROM transactions WHERE userid = '%d' OR developerid = '%d' ORDER BY date DESC", user.getUserID(),user.getUserID());
		ResultSet result = driver.executeQuery(query);
		int total = user.getCredit();
		while(result.next()){
			//get some details of the transactions
			 int appID = result.getInt("appID");
			 String appName = "";
			 for (AppBean a:allApps){
				 if(a.getAppID()==appID)
					 appName = a.getName();
			 }			 
			 int userID = result.getInt("userid");
			 int developerID = result.getInt("developerid");			 
			 int amount = result.getInt("amount");
			 Timestamp date = result.getTimestamp("date");
			 TransactionBean transaction;
			 String description = "";
			 
			 if(userID == user.getUserID()){
				 //if it is a top up
				 if(developerID==0){
					 transaction = new TransactionBean(amount,total,date);					 
				 }
				//if the user is buyer
				 else{
					 amount *= -1;
					 query = "SELECT username FROM userinfo WHERE userID=\""+developerID+"\" ";	
					 ResultSet res = new DBDriver().executeQuery(query);
					 if(res.next())
						 description = "To: "+res.getString("username");  
					 transaction = new TransactionBean(appName,description,amount,total,date);					 
				 }
			 }
			 //if the user is seller
			 else{
				 amount = result.getInt("incomeDev");				 
				 query = "SELECT username FROM userinfo WHERE userID=\""+userID+"\" ";	
				 ResultSet res = new DBDriver().executeQuery(query);
				 if(res.next())
					 description = "From: "+res.getString("username");  
				 transaction = new TransactionBean(appName,description,amount,total,date);				 
			 }
			 total -= amount; //get the credit of the user before the transaction
			 transactions.add(transaction);
		}
		return transactions;
	}
	
	//get the all the transactions for admin
	public List<TransactionBean> getAdminTransaction(UserBean user,List<AppBean> allApps)throws SQLException{
		List<TransactionBean> transactions = new ArrayList<TransactionBean>();
		String query = "SELECT *  FROM transactions ORDER BY date DESC";
		ResultSet result = driver.executeQuery(query);
		int total = user.getCredit();
		DBDriver driver2 = new DBDriver();
		while(result.next()){
			TransactionBean transaction;
			int appID = result.getInt("appID");
			int userID = result.getInt("userid");
			int amount = result.getInt("amount");
			Timestamp date = result.getTimestamp("date");
			//if it is a selling
			if(appID!=0){
				String appName = "";
				for (AppBean a:allApps){
					if(a.getAppID()==appID)
						appName = a.getName();
					}				
				int developerID = result.getInt("developerid");
				int developerIncome = result.getInt("incomeDev");				
				String description = "";				
				query = "SELECT username FROM userinfo WHERE userID=\""+userID+"\" ";
				ResultSet res = driver2.executeQuery(query);
				if(res.next())
					description = res.getString("username")+" -->  ";
				query = "SELECT username FROM userinfo WHERE userID=\""+developerID+"\" ";
				res = driver2.executeQuery(query);
				if(res.next())
					description += res.getString("username")+ " ("+amount+") ";
				 transaction = new TransactionBean(appName,description,developerIncome,total,date);
				 total -= developerIncome;
			 }
			//if it is a top up
			else{
				String description = "";				
				query = "SELECT username FROM userinfo WHERE userID=\""+userID+"\" ";
				ResultSet res = driver2.executeQuery(query);
				if(res.next())
					description = res.getString("username")+" Topup: ";
				description += amount;
				transaction = new TransactionBean("",description,0,total,date);
			}
			transactions.add(transaction);
		}
		return transactions;
	}
}
