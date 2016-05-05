package controller;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.DBDriver;
import model.TransactionBean;
import model.UserBean;

/*
 * controller.TopupServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * top up servlet
 * handle the top up action by the user
 * if success, update the database and session
 * if not, go back to top up page with error message
 * 
 */

public class TopupServlet extends HttpServlet {
	private DBDriver driver;
	private static String successURL = "MyAccount.jsp";
	private static String unsuccessURL = "MyAccount.jsp";
	private String errorMessage;
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		String password = req.getParameter("password");
		int amount = Integer.parseInt(req.getParameter("amount"));
		HttpSession session = req.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		
		try {
			DBDriver driver = new DBDriver();
			String query = "SELECT password,credit FROM userinfo WHERE username=\""+user.getUsername()+"\" ";
			ResultSet result = driver.executeQuery(query);
			//if success
			if(result.next()&&password.equals(result.getString("password"))){
				//update credit of the user
				int credit = result.getInt("credit");
				credit += amount;
				query = "UPDATE userinfo SET credit = "+credit+" WHERE userID = "+user.getUserID();
				driver.executeAddQuery(query);
				user.setCredit(credit);
				
				//add transaction record
				query = String.format("INSERT INTO transactions (userid, amount) VALUES ('%d', '%d')",
						user.getUserID(),amount);
				driver.executeAddQuery(query);				
				Timestamp date = new Timestamp(new Date().getTime());
				TransactionBean transaction = new TransactionBean(amount,credit,date);
				user.getTransactions().add(transaction);
				
				res.sendRedirect(successURL);
			}
			//if the password is wrong
			else{
				errorMessage = "The password is wrong";
				req.setAttribute("error", errorMessage);
				RequestDispatcher rd = req.getRequestDispatcher(unsuccessURL);
				rd.forward(req, res);
			}
			
		} catch (SQLException e) {
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
