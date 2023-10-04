package com.alacriti.Gateway.exception;

import com.alacriti.Gateway.Entity.Merchant;

public class MerchantAlreadyRegisteredException extends RuntimeException{

	
	 public MerchantAlreadyRegisteredException(String message) {
		 	super(message);			
	    }

}
