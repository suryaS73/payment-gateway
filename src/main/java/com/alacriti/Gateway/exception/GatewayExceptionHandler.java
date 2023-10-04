package com.alacriti.Gateway.exception;

import java.net.ConnectException;
import java.nio.channels.AsynchronousCloseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alacriti.Gateway.Controller.GatewayController;



@ControllerAdvice
public class GatewayExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GatewayExceptionHandler.class);

	
	 @ExceptionHandler(value = {MerchantAlreadyRegisteredException.class})
	    public ResponseEntity<Object> handleMerchantAlreadyRegisteredException
	            (MerchantAlreadyRegisteredException merchantAlreadyRegisteredException)
	    {
         logger.error("Error occurred while registering merchant: {}", merchantAlreadyRegisteredException.getMessage());

		 GatewayException gatewayException = new GatewayException(
				 merchantAlreadyRegisteredException.getMessage(),
				 merchantAlreadyRegisteredException.getCause(),
	                HttpStatus.OK
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.OK);
	    }
	 
	 
	 @ExceptionHandler(value = {InvalidCardDetailsException.class})
	    public ResponseEntity<Object> handleInvalidCardDetailsException
	            (InvalidCardDetailsException invalidCardDetailsException)
	    {
         logger.error("Error occurred while processing payment: {}", invalidCardDetailsException.getMessage());

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
         logger.error("Error occurred while processing payment: {}", insufficientAmountException.getMessage());

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
         logger.error("Error occurred while processing payment: {}", merchantNotFoundException.getMessage());

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
         logger.error("Error occurred while retrieving payment details: {}", paymentIdNotFoundException.getMessage());

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
         logger.error("Error occurred while processing payment: {}", noPaymentsFound.getMessage());

		 GatewayException gatewayException = new GatewayException(
				"Database " +noPaymentsFound.getMessage(),
				 noPaymentsFound.getCause(),
	                HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(value = {ConnectException.class})
	    public ResponseEntity<Object> handleConnectException
	            (ConnectException connectException)
	    {
         logger.error("Error occurred while processing payment: {}", connectException.getMessage());

		 GatewayException gatewayException = new GatewayException(
				"Database " +connectException.getMessage(),
				connectException.getCause(),
	                HttpStatus.INTERNAL_SERVER_ERROR
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	 @ExceptionHandler(value = {Exception.class})
	    public ResponseEntity<Object> handleException
	            (Exception exception)
	    {
         logger.error("Error occurred while processing payment: {}",exception.getMessage());

		 GatewayException gatewayException = new GatewayException(
				"Something went wrong Please Try again after some time",
				exception.getCause(),
	                HttpStatus.INTERNAL_SERVER_ERROR
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	 @ExceptionHandler(value = {AsynchronousCloseException.class})
	    public ResponseEntity<Object> handleAsynchronousCloseException
	            (AsynchronousCloseException asynchronousCloseException)
	    {
      logger.error("Error occurred while processing payment: {}",asynchronousCloseException.getMessage());

		 GatewayException gatewayException = new GatewayException(
				 asynchronousCloseException.getMessage(),
				asynchronousCloseException.getCause(),
	                HttpStatus.INTERNAL_SERVER_ERROR
	        );

	        return new ResponseEntity<>(gatewayException, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
