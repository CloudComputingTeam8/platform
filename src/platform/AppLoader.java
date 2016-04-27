package platform;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * AppLoader is a custom URLClassLoader for loading apps onto the Platform.
 * AppLoader works in much the same way as a URLClassLoader, which loads Java
 * classes that are found on specified URL paths.  However, AppLoader makes
 * a distinction between the app-class and other classes loaded from the same
 * package as the app, which are always reloaded, and classes which are 
 * referenced from other packages, which are retrieved from the Java runtime
 * cache if they have already been loaded once.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class AppLoader extends URLClassLoader {
	
	/**
	 * The app name as a package-qualified String, eg: "my.external.App"
	 */
	private String appName;
	
	/**
	 * The app package prefix String, eg: "my.external"
	 */
	private String appPrefix;

	/**
	 * Creates an Apploader for loading an app and its associated classes
	 * from a given package.
	 * @param appName the package-qualified name of the app, eg: "my.external.App"
	 * @param urls a singleton URL array, containing the URL from which to load.
	 * @param delegate the delegate ClassLoader, which loaded the Platform.
	 */
	public AppLoader(String appName, URL[] urls, ClassLoader delegate) {
		super(urls, delegate);
		this.appName = appName;
		this.appPrefix = getPrefix(appName);
	}
	
	/**
	 * Customised class loading algorithm, which treats app-classes differently
	 * from platform-classes.  Loading can potentially load the app-class and
	 * any other classes on which this depends, for example, superclasses, or
	 * interfaces, or indeed other component classes of the app.  We always want
	 * to upload the app class and any components living in the same package as
	 * the app, in case any of these have been modified since the platform
	 * runtime was started.  However, we don't want to re-upload superclasses,
	 * interfaces, or classes provided by the platform.  This algorithm compares
	 * every loaded class with the main app-class, to see whether it comes from
	 * the same package.  If so, it uploads it afresh.  Otherwise, it uses the
	 * default algorithm, which may retrieve from cache.
	 */
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (inAppPackage(name)) {
			System.out.println("Uploading the class: " + name);
			return findClass(name); // really upload this class afresh
		}
		else {
			System.out.println("Recovering cached class: " + name);
			return super.loadClass(name); // delegate to standard algorithm
		}
	}
	
	/**
	 * Reports whether a class name comes from the same package as the app-class.
	 * If the class name is the app class, returns true immediately.  Otherwise,
	 * splits the name into its package pathname components and compares these 
	 * against the package pathname components of the app class.  If they are 
	 * the same, returns true.  In all other cases, returns false.
	 * @param name a package-qualified class name to be loaded.
	 * @return true, if this refers to the app-class, or one of its components
	 * from the same package.
	 */
	private boolean inAppPackage(String name) {
		if (name.equals(appName))
			return true;
		else if (appPrefix == null)
			return getPrefix(name) == null;
		else
			return appPrefix.equals(getPrefix(name));
	}
	
	/**
	 * Returns the package path prefix of a package-qualified class name.
	 * If the class name has a package prefix, extracts this and returns it
	 * as a String; otherwise returns null, indicating that the class is in
	 * the default package.
	 * @param name a package-qualified class name.
	 * @return the package path prefix, or null.
	 */
	private String getPrefix(String name) {
		int dot = name.lastIndexOf('.');
		if (dot == -1)
			return null;
		else {
			String prefix = name.substring(0, dot);
			return prefix;
		}
	}

}
