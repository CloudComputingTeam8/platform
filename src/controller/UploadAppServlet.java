package controller;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.AppBean;
import model.AppDetailBean;
import model.DBDriver;
import model.UserBean;
import platform.*;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
	maxFileSize=1024*1024*10, // 10MB
	maxRequestSize=1024*1024*50) // 50MB

public class UploadAppServlet extends HttpServlet {
	 private static final String SAVE_DIR = "uploadedFiles";
	 private static final String destdir = "extractorFile";
	 private static final String imagedir = "imageFile";
	 private DBDriver driver;
	 private static String successURL = "index.jsp";

	 private String extractFileName(Part part) {
		 String contentDisp = part.getHeader("content-disposition");
		 System.out.println("contentDisp: "+contentDisp);
		 String[] items = contentDisp.split(";");
		 for (String s : items) {
			 if (s.trim().startsWith("filename")) {
				 String name = s.substring(s.lastIndexOf("\\")+1, s.length()-1);
				 System.out.println(name);
				 return name;
			 }
		 }
		 return "";
	 }

	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 String appname = req.getParameter("appName");
			 // Get absolute path of this running web application
			 String appPath = req.getServletContext().getRealPath("");
			 // Create path to the directory to save uploaded file
			 String savePath = appPath + File.separator + SAVE_DIR;
			 String extractorPath = appPath + File.separator + destdir;
			 System.out.println("save path: "+savePath);
			 // Create the save directory if it does not exist
			 File fileSaveDir = new File(savePath);
			 if (!fileSaveDir.exists())
				 fileSaveDir.mkdir();
			 Part part = req.getPart("file");
			 String fileName = extractFileName(part);
			 System.out.println("fileName: "+fileName);
			 part.write(savePath + File.separator + fileName);
			 extractor(savePath + File.separator + fileName,extractorPath);
			 
			 String imagePath = appPath + File.separator + imagedir;
			 File imageSaveDir = new File(imagePath);
			 if (!imageSaveDir.exists())
				 imageSaveDir.mkdir();
			 part = req.getPart("image");
			 fileName = extractFileName(part);
			 part.write(imagePath + File.separator + fileName);
			 
			 
			 
			 HttpSession session = req.getSession();
			 UserBean user = (UserBean) session.getAttribute("user");
			 int appID=0;
			 try {
				 driver = new DBDriver();
				 String query = String.format("INSERT INTO appinfo (name, owned, image) VALUES ('%s', '%d', '%s')", appname, user.getUserID(),fileName);
				 driver.executeAddQuery(query);	
				 query = "SELECT appID  FROM appinfo WHERE owned = "+user.getUserID();
				 ResultSet result = driver.executeQuery(query);
				 appID = result.getInt("appID");
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
					RequestDispatcher rd = req.getRequestDispatcher(successURL);
					rd.forward(req, res);
				}
			 
			 AppDetailBean app = new AppDetailBean(appname,user.getUsername(),0,appID,"Running",0,0,fileName);
			 user.getMyApps().add(app);
			 List<AppBean> allApps = (List<AppBean>) session.getAttribute("appOverview");
			 app.setAuthorisation(true);
			 allApps.add(app);
	 }
	 
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
