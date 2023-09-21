package com.alacriti.Gateway.Entity;

public class MerchantDto {

	private int id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private double accountbal;
	
	private String msg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getAccountbal() {
		return accountbal;
	}

	public void setAccountbal(double accountbal) {
		this.accountbal = accountbal;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public MerchantDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MerchantDto(int id, String name, String email, String phone, double accountbal, String msg) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.accountbal = accountbal;
		this.msg = msg;
	}
}
