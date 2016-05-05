package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/*
 * model.TransactionBean.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * TransactionBean model
 * save the information of transaction of peanut bank
 * 
 */
public class TransactionBean implements Serializable {
	private int amount,total;
	private String date,appName = "",description;
	
	public TransactionBean(){
		
	}
	
	public TransactionBean(String appName,String description,int amount,int total,Timestamp date){
		this.appName = appName;
		this.description = description;
		this.amount = amount;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		this.date = f.format(date);
		this.total = total;
	}
	
	public TransactionBean(int amount,int total,Timestamp date){
		this.description = "Topup";
		this.amount = amount;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		this.date = f.format(date);
		this.total = total;
	}
	
	public String getAppName(){
		return appName;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getTotal(){
		return total;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public String getDate(){
		return date;
	}

}
