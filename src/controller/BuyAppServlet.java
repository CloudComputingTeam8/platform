package controller;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.*;

/*
 * controller.BuyAppServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * Buy App servlet
 * When the user clicks the "buy" button from the dashboard, this servlet is called to handle this action
 * the appID of app to buy is get
 * if the action success, the servlet update database and session, then point back to dashboard with updated data
 * if not go back to dashboard as well with an error message
 * 
 */

public class BuyAppServlet extends HttpServlet {
	private DBDriver driver;
	private static String successURL = "index.jsp";
	private String errorMessage;
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		int appID = Integer.parseInt(req.getParameter("appID")); //get appID
		
		try {
			driver = new DBDriver();
			HttpSession session = req.getSession();
			UserBean user = (UserBean) session.getAttribute("user");
			
			//get app information
			int price = 0, credit = 0, developerID = 0,income = 0;
			String appName = "";
			String query = "SELECT * FROM appinfo WHERE appID=\""+appID+"\" ";
			ResultSet result = driver.executeQuery(query);			
			if(result.next()){
				price = result.getInt("price");
				developerID = result.getInt("owned");
				income = result.getInt("income");
				appName = result.getString("name");
			}
			
			//get the credit of user
			query = "SELECT credit FROM userinfo WHERE userID=\""+user.getUserID()+"\" ";
			result = driver.executeQuery(query);			
			if(result.next())
				credit = result.getInt("credit");
			//if the credit is not enough to buy the app, go back to dashboard with error message
			if(credit<price){
				errorMessage = "Run out of credit";
				req.setAttribute("error", errorMessage);
				RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
				rd.forward(req, res);	
			}
			//if success
			else{
				int incomeDev = (int) (price * 0.8); //80% credit goes to app developer
				int incomeAda = price - incomeDev; // the platform get the rest credit
				credit -= price; //the user credit is subtracted
				income += incomeDev; //update the total income of the app
				query = "UPDATE userinfo SET credit = "+credit+" WHERE userID = "+user.getUserID(); //update database
				driver.executeAddQuery(query);
				user.setCredit(credit); //update session
				
				//update the credit of developer
				query = "SELECT credit FROM userinfo WHERE userID=\""+developerID+"\" ";
				result = driver.executeQuery(query);
				int developerCredit = 0;
				if(result.next())
					developerCredit = result.getInt("credit") + incomeDev;
				query = "UPDATE userinfo SET credit = "+developerCredit+" WHERE userID = "+developerID;
				driver.executeAddQuery(query);
				
				//update the credit of platform admin
				query = "SELECT credit FROM userinfo WHERE authority=3";
				result = driver.executeQuery(query);
				int adminCredit = 0;
				if(result.next())
					adminCredit = result.getInt("credit") + incomeAda;
				query = "UPDATE userinfo SET credit = "+adminCredit+" WHERE authority=3";
				driver.executeAddQuery(query);
				
				//update app information and create transactions
				query = "UPDATE appinfo SET income = "+income+" WHERE appID = "+appID;
				driver.executeAddQuery(query);				
				query = String.format("INSERT INTO transactions "
						+ "(appid, userid, developerid, amount, incomeDev, incomeAda) VALUES ('%d', '%d','%d', '%d','%d', '%d')",
						appID, user.getUserID(),developerID,price,incomeDev,incomeAda);
				driver.executeAddQuery(query);
				
				//update the authorization of user
				query = String.format("INSERT INTO appauth (appID, userID) VALUES ('%d', '%d')",appID, user.getUserID());
				driver.executeAddQuery(query);
				
				//update session
				List<AppBean> allApps = (List<AppBean>) session.getAttribute("appOverview");
				for(AppBean app:allApps){
					if(app.getAppID() == appID) {
						app.setAuthorisation(true);
						user.getPurchasedApps().add(app);
					}
				}
				Timestamp date = new Timestamp(new Date().getTime());
				query = "SELECT username FROM userinfo WHERE userID=\""+developerID+"\" ";
				result = driver.executeQuery(query);
				String description = "";
				if(result.next())
					description = "To: "+result.getString("username");
				TransactionBean transaction = new TransactionBean(appName,description,price,credit,date);
				user.getTransactions().add(transaction);
				
				res.sendRedirect(successURL); //go to dashboard
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
}
