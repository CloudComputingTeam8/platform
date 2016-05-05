package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

/*
 * controller.LogoutServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * logout servlet
 * handle the logout action by invalidate the session
 * then goes to login page
 * 
 */

public class LogoutServlet extends HttpServlet {
	private static String successURL = "LogIn.jsp";
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		session.invalidate();
		res.sendRedirect(successURL);
	}

}
