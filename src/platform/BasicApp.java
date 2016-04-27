package platform;

/**
 * BasicApp is a mock-up of some platform-basic service that is supplied by
 * the platform.  It is compiled along with the platform, so there should be
 * no problem running an instance of this app.  BasicApp satisfies the
 * AppInterface used by the platform.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class BasicApp implements AppInterface {

	/**
	 * Constructs a default BasicApp
	 */
	public BasicApp() {
	}

	/**
	 * Starts up this BasicApp.  Prints a message to standard output.
	 */
	@Override
	public void start() {
		System.out.println("Starting a BasicApp");
	}

	/**
	 * Stops this BasicApp.  Prints a message to standard output.
	 */
	@Override
	public void stop() {
		System.out.println("Stopping a BasicApp");
	}

}
