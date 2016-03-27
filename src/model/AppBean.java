package model;

import java.io.Serializable;

public class AppBean implements Serializable {
	private String name;
	private String owned;
	private int price;
	private int appID;
	private boolean authorisation = false; 
	
	public AppBean(){		
	}
	
	public AppBean(String name, String owned, int price, int appID){
		this.name = name;
		this.owned = owned;
		this.price = price;
		this.appID = appID;
	}
	
	public void setAuthorisation(boolean authorisation){
		this.authorisation = authorisation;
	}
	
	public String getName(){
		return name;
	}

	public String getOwned(){
		return owned;
	}
	
	public int getPrice(){
		return price;
	}
	
	public int getAppID(){
		return appID;
	}
	
	public boolean getAuthorisation(){
		return authorisation;
	}
}
