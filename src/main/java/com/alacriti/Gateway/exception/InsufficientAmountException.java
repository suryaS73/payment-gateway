package com.alacriti.Gateway.exception;

public class InsufficientAmountException extends RuntimeException{
	 public InsufficientAmountException(String message) {
	        super(message);
	    }

	    public InsufficientAmountException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
