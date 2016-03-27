package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.DBDriver;

public class SignUpServlet extends HttpServlet {
	private static String successURL = "/login";
	private static String unsuccessURL = "try.jsp";
	private String errorMessage;
	private DBDriver driver;

	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String username = req.getParameter("userName");
		String password = req.getParameter("password");
		String password2 = req.getParameter("password2");
		
		if(password.equals(password2)){
			try {
				driver = new DBDriver();
				String query = String.format("INSERT INTO userinfo (username, password) VALUES ('%s', '%s')", username, password);
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
				req.setAttribute("userName", username);
				req.setAttribute("password", password);
				RequestDispatcher rd = req.getRequestDispatcher(successURL);
				rd.forward(req, res);
			}			
		}
		else {
			errorMessage = "Please type in same passwords";
			req.setAttribute("error", errorMessage);
			RequestDispatcher rd = req.getRequestDispatcher(unsuccessURL);
			rd.forward(req, res);
		}
		
	}
}
