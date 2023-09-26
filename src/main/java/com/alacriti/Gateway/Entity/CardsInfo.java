package com.alacriti.Gateway.Entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

//@Entity
//@Table(name="cardsinfo")
public class CardsInfo {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String cardno;
	
	private int cvv;
	
	private Date validity;
	
	private double avlbal;

	public CardsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CardsInfo(int id, String cardno, int cvv, Date validity, double avlbal) {
		super();
		this.id = id;
		this.cardno = cardno;
		this.cvv = cvv;
		this.validity = validity;
		this.avlbal = avlbal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public double getAvlbal() {
		return avlbal;
	}

	public void setAvlbal(double avlbal) {
		this.avlbal = avlbal;
	}
	
	

}
