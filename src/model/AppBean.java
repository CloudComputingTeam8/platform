package model;

import java.io.Serializable;

/*
 * model.AppBean.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * AppBean model
 * save the general information of the app 
 * 
 */

public class AppBean implements Serializable {
	private String name;
	private String owned;
	private int price;
	private int appID;
	private boolean authorisation = false; 
	private String image;
	private String status,description;
	
	public AppBean(){		
	}
	
	public AppBean(String name, String owned, int price, int appID, String image, String status,String description){
		this.name = name;
		this.owned = owned;
		this.price = price;
		this.appID = appID;
		this.image = image;
		this.status = status;
		this.description = description;
	}
	
	public void setAuthorisation(boolean authorisation){
		this.authorisation = authorisation;
	}
	
	public void setStatus(String status){
		this.status = status;
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
	
	public String getImage(){
		return image;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getDescription(){
		return description;
	}
}
