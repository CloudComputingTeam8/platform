package controller;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
	maxFileSize=1024*1024*10, // 10MB
	maxRequestSize=1024*1024*50) // 50MB

/*
 * controller.UploadAppServlet.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * Upload App Servlet
 * handle the upload action,
 * upload the zip file as a .jar or .war extension,
 * extractor the file and save it under the path of the app name
 * update database
 * goes to dashboard
 * 
 */

public class UploadAppServlet extends HttpServlet {
	 private static final String SAVE_DIR = "uploadedFiles";
	 private static final String destdir = "extractorFile";
	 private static final String imagedir = "imageFile";
	 private DBDriver driver;
	 private static String successURL = "index.jsp";

	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 String appname = req.getParameter("appName");
			 // Get absolute path of this running web application
			 String appPath = req.getServletContext().getRealPath("");
			 // Create path to the directory to save uploaded file, and extract file
			 String savePath = appPath + File.separator + SAVE_DIR;
			 String extractorPath = appPath + File.separator + destdir + File.separator + appname;
			 // Create the save directory if it does not exist
			 File fileSaveDir = new File(savePath);
			 if (!fileSaveDir.exists())
				 fileSaveDir.mkdir();
			 Part part = req.getPart("file");
			 String fileName = extractFileName(part);
			 System.out.println("fileName: "+fileName);
			 part.write(savePath + File.separator + fileName);
			 extractor(savePath + File.separator + fileName,extractorPath);
			 
			 //save the icon image of the app
			 String imagePath = appPath + File.separator + imagedir;
			 File imageSaveDir = new File(imagePath);
			 if (!imageSaveDir.exists())
				 imageSaveDir.mkdir();
			 part = req.getPart("image");
			 fileName = extractFileName(part);
			 part.write(imagePath + File.separator + fileName);
			 
			 //update the session and database
			 String description = req.getParameter("description");
			 int price = Integer.parseInt(req.getParameter("price"));
			 String type = req.getParameter("type");			 
			 HttpSession session = req.getSession();
			 UserBean user = (UserBean) session.getAttribute("user");
			 int appID=0;
			 try {
				 driver = new DBDriver();
				 String query = String.format("INSERT INTO appinfo (name, owned, image, price, description, status) VALUES ('%s','%d','%s','%d','%s','%s')", 
						 appname, user.getUserID(),fileName,price,description,type);
				 appID = driver.executeAddQuery(query);			 
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
			 AppDetailBean app = new AppDetailBean(appname,user.getUsername(),price,appID,type,0,0,fileName,description);
			 user.getMyApps().add(app);
			 List<AppBean> allApps = (List<AppBean>) session.getAttribute("appOverview");
			 app.setAuthorisation(true);
			 allApps.add(app);
			 RequestDispatcher rd = req.getRequestDispatcher(successURL);
			 rd.forward(req, res);
	 }
	 
	 //extract file name
	 private String extractFileName(Part part) {
		 String contentDisp = part.getHeader("content-disposition");
		 System.out.println("contentDisp: "+contentDisp);
		 String[] items = contentDisp.split(";");
		 for (String s : items) {
			 if (s.trim().startsWith("filename")) {
				 //String name = s.substring(s.lastIndexOf("\\")+1, s.length()-1); //for eclipse version
				 String name =  s.substring(s.indexOf("=") + 2, s.length()-1); //for tomcat version
				 System.out.println(name);
				 return name;
			 }
		 }
		 return "";
	 }
	 
	 //extract the file
	 public void extractor(String jarpath, String extractorPath)  throws IOException{
		 JarFile jarfile = new JarFile(jarpath);
		 for (Enumeration<JarEntry> iter = jarfile.entries(); iter.hasMoreElements();) {
			 JarEntry entry = iter.nextElement();
			 System.out.println("Processing: " + entry.getName());
			 File outfile = new File(extractorPath, entry.getName());
			 if (! outfile.exists())
				 outfile.getParentFile().mkdirs();
			 if (! entry.isDirectory()) {
				 InputStream instream = jarfile.getInputStream(entry);
				 FileOutputStream outstream = new FileOutputStream(outfile);
				 while (instream.available() > 0) {
					 outstream.write(instream.read());
				 }
				 outstream.close();
				 instream.close();
			 } // end if
		 } // end for
		 jarfile.close();
	 }

}
