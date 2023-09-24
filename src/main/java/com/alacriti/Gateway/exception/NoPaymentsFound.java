package com.alacriti.Gateway.exception;

public class NoPaymentsFound extends RuntimeException{

	 public NoPaymentsFound(String message) {
	        super(message);
	    }

	    public NoPaymentsFound(String message, Throwable cause) {
	        super(message, cause);
	    }
}
