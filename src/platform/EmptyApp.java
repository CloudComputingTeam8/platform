package platform;

/**
 * EmptyApp is a mock-up of the app that does nothing.  We use this in place
 * of apps when we cannot find the app we really wanted, perhaps because we
 * misspelled its pathname or class name.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class EmptyApp  implements AppInterface {

	/**
	 * Creates this EmptyApp.
	 */
	public EmptyApp() {
	}

	/**
	 * Starts this EmptyApp; does nothing.
	 */
	@Override
	public void start() {
	}

	/**
	 * Stops this EmptyApp; does nothing.
	 */
	@Override
	public void stop() {
	}

}
