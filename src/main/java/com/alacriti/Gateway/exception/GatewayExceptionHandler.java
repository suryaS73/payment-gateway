package com.alacriti.Gateway.exception;

import java.net.ConnectException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GatewayExceptionHandler {

	 @ExceptionHandler(value = {MerchantAlreadyRegisteredException.class})
	    public ResponseEntity<Object> handleMerchantAlreadyRegisteredException
	            (MerchantAlreadyRegisteredException merchantAlreadyRegisteredException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 merchantAlreadyRegisteredException.getMessage(),
				 merchantAlreadyRegisteredException.getCause(),
	                HttpStatus.CONFLICT
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.CONFLICT);
	    }
	 
	 @ExceptionHandler(value = {InvalidCardDetailsException.class})
	    public ResponseEntity<Object> handleInvalidCardDetailsException
	            (InvalidCardDetailsException invalidCardDetailsException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 invalidCardDetailsException.getMessage(),
				 invalidCardDetailsException.getCause(),
	                HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(value = {InsufficientAmountException.class})
	    public ResponseEntity<Object> handleInsufficientAmountException
	            (InsufficientAmountException insufficientAmountException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 insufficientAmountException.getMessage(),
				 insufficientAmountException.getCause(),
	                HttpStatus.CONFLICT
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.CONFLICT);
	    }
	 
	 @ExceptionHandler(value = {MerchantNotFoundException.class})
	    public ResponseEntity<Object> handleMerchantNotFoundException
	            (MerchantNotFoundException merchantNotFoundException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 merchantNotFoundException.getMessage(),
				 merchantNotFoundException.getCause(),
	                HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(value = {PaymentIdNotFoundException.class})
	    public ResponseEntity<Object> handlePaymentIdNotFoundException
	            (PaymentIdNotFoundException paymentIdNotFoundException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 paymentIdNotFoundException.getMessage(),
				 paymentIdNotFoundException.getCause(),
	                HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(value = {NoPaymentsFound.class})
	    public ResponseEntity<Object> handleNoPaymentsFound
	            (NoPaymentsFound noPaymentsFound)
	    {
		 GatewayException gatewayException = new GatewayException(
				 noPaymentsFound.getMessage(),
				 noPaymentsFound.getCause(),
	                HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(value = {ConnectException.class})
	    public ResponseEntity<Object> handleNoPaymentsFound
	            (ConnectException connectException)
	    {
		 GatewayException gatewayException = new GatewayException(
				 connectException.getMessage(),
				 connectException.getCause(),
	                HttpStatus.INTERNAL_SERVER_ERROR
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
