package model;

/*
 * model.AppDetailBean.java  	1.0 05/05/2016
 * Assignment of COM6519 Cloud Computing
 * Written by Wenyi Hu (acp15wh)
 * 
 * AppDetailBean model
 * subclass of AppBean
 * save the extra information that only accessed by the developer 
 * 
 */

public class AppDetailBean extends AppBean {
	private int pv, income;
	public AppDetailBean(){
		
	}
	
	public AppDetailBean(String name, String owned, int price, int appID, 
			String status, int pv, int income, String image, String description){
		super(name,owned,price,appID,image,status,description);
		this.pv = pv;
		this.income = income;
	}
	
	public int getPV(){
		return pv;
	}
	
	public int getIncome(){
		return income;
	}
}
