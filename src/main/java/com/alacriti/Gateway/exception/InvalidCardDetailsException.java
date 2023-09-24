package com.alacriti.Gateway.exception;

public class InvalidCardDetailsException extends RuntimeException{
	 public InvalidCardDetailsException(String message) {
	        super(message);
	    }

	    public InvalidCardDetailsException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
