package platform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AppInterface is a mock-up of the kind of interface that Apps may have to
 * respect.  The Platform class expects all of its Apps to implement this
 * interface.
 * @author Anthony J H Simons
 * @version 1.0
 */
public interface AppInterface {
	
	/**
	 * Start the App
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	

}
