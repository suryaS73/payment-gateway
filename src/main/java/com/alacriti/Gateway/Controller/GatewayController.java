package com.alacriti.Gateway.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Service.GatewayServiceImp;
import com.alacriti.Gateway.response.ResponseHandler;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

	private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

	@Autowired
	private GatewayServiceImp service;

	@PostMapping("/merchant/registration")
	public ResponseEntity<Object> registerMerchant(@RequestBody Merchant merchant) {
		logger.info("Received a merchant registration request for merchant: {}", merchant.getName());
		return ResponseHandler.responseBuilder("Requested Merchant is registered successfully", HttpStatus.CREATED,
				service.merchantRegistrationService(merchant));

	}

	@PostMapping("/merchant/payment")
	public ResponseEntity<Object> payment(@RequestBody PaymentInfo paymentInfo) {
		logger.info("Received a payment request for merchant: {}", paymentInfo.getMerchantid());
		return ResponseHandler.responseBuilder("Payment Successful", HttpStatus.ACCEPTED, service.payment(paymentInfo));

	}

	@GetMapping("/merchant/paymentstatus/{paymentId}")
	public ResponseEntity<Object> paymentStatus(@PathVariable int paymentId) {
		logger.info("Received a request to retrieve payment details for Payment ID: {}", paymentId);

		return ResponseHandler.responseBuilder("Your Payment Details", HttpStatus.OK,
				service.getPaymentDetailsByPaymentId(paymentId));

	}

	@GetMapping("/merchant/paymentslist/{merchantName}")
	public ResponseEntity<Object> fetchPaymentListByMerchantPartialName(
			@PathVariable("merchantName") String merchantName) {
		logger.info("Received a request to fetch payment details for Merchant: {}", merchantName);
		// Your payment list retrieval logic
		return ResponseHandler.responseBuilder("Payment Details", HttpStatus.OK,
				service.fetchPaymentsByMerchantName(merchantName));

	}
}
