package com.alacriti.Gateway.Entity;


public class PaymentInfo {
	
	private int merchantid;
	
	private double amount;
	
	private String cardno;

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

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public PaymentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentInfo(int merchantid, double amount, String cardno) {
		super();
		this.merchantid = merchantid;
		this.amount = amount;
		this.cardno = cardno;
	}
	
	

}
