package com.alacriti.Gateway.Entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

//@Entity
//@Table(name="merchant")
public class Merchant {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private double accountbal;
	
	

	public Merchant(int id, String name, String email, String phone, double accountbal) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.accountbal = accountbal;
	}

	public Merchant() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	
	

}
