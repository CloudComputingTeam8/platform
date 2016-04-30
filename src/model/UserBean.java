package model;

import java.io.Serializable;
import java.util.List;

public class UserBean implements Serializable {
	private int userID, authorisation,credit;
	private String username;
	private List<AppBean> purchasedApps;
	private List<AppDetailBean> myApps;
	
	public UserBean() {		
	}
	
	public UserBean(String username, int authorisation, int userID,int credit){
		this.username = username;
		this.authorisation = authorisation;
		this.userID = userID;
		this.credit = credit;
	}
	
	public void addPurchasedApps(List<AppBean> purchasedApps) {
		this.purchasedApps = purchasedApps;
	}
	
	public void addMyApps(List<AppDetailBean> myApps) {
		this.myApps = myApps;
	}
	
	public void setCredit(int credit){
		this.credit = credit;
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
	
	public int getCredit(){
		return credit;
	}

	public List<AppBean> getPurchasedApps() {
		return purchasedApps;
	}
	
	public List<AppDetailBean> getMyApps() {
		return myApps;
	}
}
