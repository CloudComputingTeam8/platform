package model;

import java.io.Serializable;
import java.util.List;

public class UserBean implements Serializable {
	private int userID, authorisation;
	private String username;
	private List<AppBean> purchasedApps;
	private List<AppDetailBean> myApps;
	
	public UserBean() {		
	}
	
	public UserBean(String username, int authorisation, int userID){
		this.username = username;
		this.authorisation = authorisation;
		this.userID = userID;
	}
	
	public void addPurchasedApps(List<AppBean> purchasedApps) {
		this.purchasedApps = purchasedApps;
	}
	
	public void addMyApps(List<AppDetailBean> myApps) {
		this.myApps = myApps;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public int getAuthorisation(){
		return authorisation;
	}
	
	public String getUsername(){
		return username;
	}

	public List<AppBean> getPurchasedApps() {
		return purchasedApps;
	}
	
	public List<AppDetailBean> getMyApps() {
		return myApps;
	}
}
