package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.*;

/*
 * controller.DeleteAppServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * Delete App servlet
 * When the app developer want to delete the app, this servlet is called to handle this action
 * a list of appID of appID to delete is get
 * if the action success, the servlet delete all the files under the app path,
 * update database and session, then update the myApp page
 * 
 */

public class DeleteAppServlet extends HttpServlet {
	 private static final String destdir = "extractorFile";
	 private static String successURL = "myApp.jsp";
	 private String currentPath;
	 private DBDriver driver;
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		String[] delete = req.getParameterValues("delete"); //get the list of appID
		currentPath = req.getServletContext().getRealPath(""); //get current path
		try {
			driver = new DBDriver();
			for (String s:delete){ //for each app to be delete
				int appID = Integer.parseInt(s);
				deleteApp(appID); //delete app
				//update app status
				HttpSession session = req.getSession();
				UserBean user = (UserBean) session.getAttribute("user");
				List<AppBean> allApps = (List<AppBean>) session.getAttribute("appOverview");
				//if the delete action is done by app developer
				if(user.getMyApps() != null){
					for(int i=0;i<user.getMyApps().size();i++){
						AppDetailBean app = user.getMyApps().get(i);
						if(appID==app.getAppID())
							app.setStatus("Delete");;
					}
				}
				//if the delete action is done by admin
				else{
					successURL = "admin.jsp";
				}
				//update session
				for(int i=0;i<allApps.size();i++){
					AppBean app = allApps.get(i);
					if(appID==app.getAppID())
						app.setStatus("Delete");;
				}
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
		res.sendRedirect(successURL);
	}
	
	//delete the app
	public void deleteApp(int appID) throws SQLException{
		//get the file path of the app
		String query = "SELECT name  FROM appinfo WHERE appID = "+appID;
		ResultSet result = driver.executeQuery(query);
		String appName = "";
		if(result.next())
			appName = result.getString("name");
		String savePath = currentPath + File.separator + destdir + File.separator + appName;
		delFolder(savePath); //delete file
		query = "UPDATE appinfo SET status = \"Delete\" WHERE appID = "+appID; //update database
		driver.executeAddQuery(query);
	}
	
	//delete the file, the code is found in Internet
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete();
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	//delete the file, the code is found in Internet
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				flag = true;
				}
			}
		return flag;
		}
}
