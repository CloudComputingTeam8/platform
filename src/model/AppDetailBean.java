package model;

public class AppDetailBean extends AppBean {
	private String status;
	private int pv, income;
	public AppDetailBean(){
		
	}
	
	public AppDetailBean(String name, String owned, int price, int appID, 
			String status, int pv, int income, String image){
		super(name,owned,price,appID,image);
		this.status = status;
		this.pv = pv;
		this.income = income;
	}
	
	public String getStatus(){
		return status;
	}
	
	public int getPV(){
		return pv;
	}
	
	public int getIncome(){
		return income;
	}
}
