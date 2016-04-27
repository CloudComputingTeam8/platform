package platform;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import platform.BasicApp;
import platform.EmptyApp;

/**
 * Platform is a mock-up of an app-hosting Platform, which demonstrates how
 * new apps may be uploaded dynamically.  This mock-up has a main() method,
 * which creates and then runs an instance of this Platform.  The run() 
 * method installs and executes a number of apps, in the following order:
 * 
 * The first example is a platform-basic BasicApp, which belongs to the 
 * same package as this Platform, so need not be uploaded.  The printout
 * shows that this app can be started and stopped.
 * 
 * The second example is a Custom app, which respects the same AppInterface
 * as the BasicApp, but which is uploaded from an external directory.  The
 * behaviour of the AppLoader is reported, as it loads this CustomApp and
 * checks that other classes on which it depends are also loaded.  The
 * loaded app is started and stopped.
 * 
 * The third example is a ComplexApp, which depends on other components that
 * are either external or already on the platform.  This example demonstrates
 * in more detail how the AppLoader only reloads those classes that came in
 * the same package as the external ComplexApp, but seeks to find other
 * classes from the Java runtime cache.  This app is started and stopped, 
 * and also causes a component app to start and stop.
 * 
 * The fourth example is an external app which does not exist, just to test
 * what the behaviour of the loader is when the class cannot be found.
 * 
 * Note that, at compile-time, this Platform only knows about the classes in
 * the "platform" package.  It has no direct references to any classes in the
 * "external" package.  The binary files for external apps have been copied
 * to the directory "/apps" (a subdirectory of the current working directory).
 * It is in here that the AppLoader looks when seeking apps to upload.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class Platform {

	/**
	 * Creates and runs an instance of this Platform.
	 * @param args
	 */
	public static void main(String[] args) {
		Platform plat = new Platform();
		plat.run();
	}
	
	/**
	 * The currently running app.  This assumes we only run one
	 * app at a time, which is perhaps a simplification.
	 */
	private AppInterface app = null;
	
	/**
	 * The directory where new apps are installed as class-files.
	 * This by default is the "/apps" subdirectory of the current
	 * working directory.
	 */
	private File appDirectory = null;
	
	/**
	 * Creates a Platform.
	 */
	public Platform() {
	}
	
	public void setAppDirectory(String path) {
		if (appDirectory == null) {
			this.appDirectory = new File(path + "/apps");
		}
	}
	
	/**
	 * Runs this Platform.  Runs a series of tests, loading and running a
	 * series of apps.  1) Runs a BasicApp that is provided by the platform.
	 * 2) Runs a CustomApp that is uploaded by the AppLoader.  3) Runs a
	 * ComplexApp that is also uploaded, but which depends on a mix of either
	 * uploaded, or platform components.  4) Tries to run an app which does
	 * not exist, to show this case.
	 */
	public void run() {
		// Sets current app to an internal platform-basic app.
		app = getBasicApp("platform.BasicApp");
		app.start();
		app.stop();
		// Sets current app to an uploaded external custom app.
		app = getCustomApp("external.CustomApp");
		app.start();
		app.stop();
		// Sets the current app to an uploaded complex app that uses other apps.
		app = getCustomApp("external.ComplexApp");
		app.start();
		app.stop();
		// Sets the current app to an external app that does not actually exist!
		app = getCustomApp("external.NoApp");
		app.start();
		app.stop();
	}
	
	/**
	 * Returns an instance of an internal app, given its package-qualified path
	 * name.  In this demo, the name argument is not used, but we just return
	 * an instance of BasicApp, which is one of the apps supplied with the
	 * Platform.
	 * @param appName the package-qualified pathname of the BasicApp.
	 * @return an instance of a BasicApp.
	 */
	private AppInterface getBasicApp(String appName) {
		return new BasicApp();
	}
	
	/**
	 * Returns an instance of an external app, given its package-qualified path 
	 * name.  Uses an AppLoader to upload the app's compiled class-file from the
	 * current appDirectory, and creates an instance of this class.  The current
	 * appDirectory is by default the "/apps" subdirectory of the current working
	 * directory.
	 * @param appName the package-qualified path name to the app-class.
	 * @return an instance of the loaded app.
	 */
	private AppInterface getCustomApp(String appName) {
		ClassLoader loader = getClassLoader(appName);
		try {
			Class<?> appClass = loader.loadClass(appName);  // load the app class
			return (AppInterface) appClass.newInstance();  // create app instance
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Could not find app class called: " + appName);
		}
		catch (InstantiationException | IllegalAccessException ex) {
			System.out.println("Could not create an instance of: " + appName);
		}
		return new EmptyApp();  // return a default EmptyApp.
	}
	
	/**
	 * Returns the app directory.  If none has been set, uses a subdirectory
	 * of the current working directory called "/apps".  You will have to
	 * put the class-files for any apps in here.
	 * @return the app directory.
	 */
	private File getAppDirectory() {
		 
		System.out.println("Accessing app directory: " + appDirectory);
		return appDirectory;
	}
	
	/**
	 * Returns a ClassLoader for loading unknown apps from the appDirectory.
	 * Here, we use our own AppLoader, which .  
	 * @return an AppLoader, our own ClassLoader.
	 */
	private ClassLoader getClassLoader(String appName) {
		ClassLoader delegate = getClass().getClassLoader(); // my ClassLoader
		File directory = getAppDirectory();
		try {
			URL[] urls = { directory.toURI().toURL() }; // convert to URL array
			return new AppLoader(appName, urls, delegate);
		} 
		catch (MalformedURLException e) {
			System.out.println("Could not create a URLClassLoader with bad URL");
			return delegate;  // will attempt to load with delegate instead
		}
	}
	
	
}
