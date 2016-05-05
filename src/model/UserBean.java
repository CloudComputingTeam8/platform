package model;

import java.io.Serializable;
import java.util.List;

/*
 * model.UserBean.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * UserBean model
 * save the information of the user
 * 
 */

public class UserBean implements Serializable {
	private int userID, authorisation,credit;
	private String username;
	private List<AppBean> purchasedApps;
	private List<AppDetailBean> myApps;
	private List<TransactionBean> transactions;
	
	public UserBean() {		
	}
	
	public UserBean(String username, int authorisation, int userID,int credit){
		this.username = username;
		this.authorisation = authorisation;
		this.userID = userID;
		this.credit = credit;
	}
	
	public void setPurchasedApps(List<AppBean> purchasedApps) {
		this.purchasedApps = purchasedApps;
	}
	
	public void setMyApps(List<AppDetailBean> myApps) {
		this.myApps = myApps;
	}
	
	public void setTransactions(List<TransactionBean> transactions) {
		this.transactions = transactions;
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
	
	public List<TransactionBean> getTransactions() {
		return transactions;
	}
}
