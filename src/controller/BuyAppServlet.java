package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AppBean;
import model.AppDetailBean;
import model.DBDriver;
import model.UserBean;
import platform.AppInterface;

public class BuyAppServlet extends HttpServlet {
	private DBDriver driver;
	private static String successURL = "index.jsp";
	private String errorMessage;
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		int appID = Integer.parseInt(req.getParameter("appID"));
		
		try {
			driver = new DBDriver();
			HttpSession session = req.getSession();
			UserBean user = (UserBean) session.getAttribute("user");
			
			int price = 0, credit = 0, developerID = 0,income = 0;
			String query = "SELECT price,owned,income FROM appinfo WHERE appID=\""+appID+"\" ";
			ResultSet result = driver.executeQuery(query);			
			if(result.next()){
				price = result.getInt("price");
				developerID = result.getInt("owned");
				income = result.getInt("income");
			}
			query = "SELECT credit FROM userinfo WHERE userID=\""+user.getUserID()+"\" ";
			result = driver.executeQuery(query);			
			if(result.next())
				credit = result.getInt("credit");
			if(credit<price){
				errorMessage = "Run out of credit";
				RequestDispatcher rd = req.getRequestDispatcher("try.jsp");
				rd.forward(req, res);	
			}
			else{
				credit -= price;
				income += price;
				query = "UPDATE userinfo SET credit = "+credit+" WHERE userID = "+user.getUserID();
				driver.executeAddQuery(query);
				user.setCredit(credit);
				query = "SELECT credit FROM userinfo WHERE userID=\""+developerID+"\" ";
				result = driver.executeQuery(query);
				int developerCredit = 0;
				if(result.next())
					developerCredit = result.getInt("credit") + price;
				query = "UPDATE userinfo SET credit = "+developerCredit+" WHERE userID = "+developerID;
				driver.executeAddQuery(query);
				query = "UPDATE appinfo SET income = "+income+" WHERE appID = "+appID;
				driver.executeAddQuery(query);
				query = String.format("INSERT INTO transactions "
						+ "(appid, userid, developerid, amount) VALUES ('%d', '%d','%d', '%d')",
						appID, user.getUserID(),developerID,price);
				driver.executeAddQuery(query);
				query = String.format("INSERT INTO appauth (appID, userID) VALUES ('%d', '%d')",appID, user.getUserID());
				driver.executeAddQuery(query);
				
				List<AppBean> allApps = (List<AppBean>) session.getAttribute("appOverview");
				for(AppBean app:allApps){
					if(app.getAppID() == appID) {
						app.setAuthorisation(true);
						user.getPurchasedApps().add(app);
					}
				}
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
		
		res.sendRedirect(successURL);
	}

}
