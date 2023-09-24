package com.alacriti.Gateway.exception;

public class MerchantAlreadyRegisteredException extends RuntimeException{

	 public MerchantAlreadyRegisteredException(String message) {
	        super(message);
	    }

	    public MerchantAlreadyRegisteredException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
