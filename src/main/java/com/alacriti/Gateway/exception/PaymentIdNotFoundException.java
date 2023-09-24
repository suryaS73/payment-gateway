package com.alacriti.Gateway.exception;

public class PaymentIdNotFoundException extends RuntimeException{
	 public PaymentIdNotFoundException(String message) {
	        super(message);
	    }

	    public PaymentIdNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
