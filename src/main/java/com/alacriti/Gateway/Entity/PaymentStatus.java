package com.alacriti.Gateway.Entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

//@Entity
//@Table(name = "paymentstatus")
public class PaymentStatus {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int merchantid;
	
	private String merchantName;
	
	private double amount;
	
	private String status;

	public PaymentStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public PaymentStatus(int id, int merchantid, String merchantName, double amount, String status) {
		super();
		this.id = id;
		this.merchantid = merchantid;
		this.merchantName = merchantName;
		this.amount = amount;
		this.status = status;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(int merchantid) {
		this.merchantid = merchantid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	

}
