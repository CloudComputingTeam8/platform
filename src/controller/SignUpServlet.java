package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.DBDriver;

/*
 * controller.SignUpServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * sign up servlet
 * sign up for a new user,
 * the user can choose to sign up as a developer user or a general user
 * if success, point to login servlet
 * if not, go back to login page with error message
 * 
 */

public class SignUpServlet extends HttpServlet {
	private static String successURL = "/login";
	private static String unsuccessURL = "LogIn.jsp";
	private String errorMessage;
	private DBDriver driver;

	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String password2 = req.getParameter("pwdRepeat");
		String[] userAuth = req.getParameterValues("userAuth");
		
		if(password.equals(password2)){
			try {
				driver = new DBDriver();
				String query = "SELECT * FROM userinfo WHERE username=\""+username+"\" ";
				ResultSet result = driver.executeQuery(query);
				//if the username is already registered by another user
				if(result.next()){
					errorMessage = "Username already exits";
					req.setAttribute("error", errorMessage);
					RequestDispatcher rd = req.getRequestDispatcher(unsuccessURL);
					rd.forward(req, res);
					}
				else{
					//if it is a normal user
					if(userAuth == null)
						query = String.format("INSERT INTO userinfo (username, password) VALUES ('%s', '%s')", username, password);
					//if it is a developer user
					else
						query = String.format("INSERT INTO userinfo (username, password, authority) VALUES ('%s', '%s', '%d')", username, password,2);
				driver.executeAddQuery(query);
				req.setAttribute("username", username);
				req.setAttribute("password", password);
				RequestDispatcher rd = req.getRequestDispatcher(successURL);
				rd.forward(req, res);
				}
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
		//if the passwords do not match
		else {
			errorMessage = "Please type in same passwords";
			req.setAttribute("error", errorMessage);
			RequestDispatcher rd = req.getRequestDispatcher(unsuccessURL);
			rd.forward(req, res);
		}		
	}
}
