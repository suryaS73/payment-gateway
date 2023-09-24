package com.alacriti.Gateway.Controller;

import java.util.List;

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
import com.alacriti.Gateway.Entity.PaymentStatus;
import com.alacriti.Gateway.Service.GatewayServiceImp;
import com.alacriti.Gateway.response.ResponseHandler;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

	@Autowired
	private GatewayServiceImp service;

	@PostMapping("/merchant/registration")
	public ResponseEntity<Object> registerMerchant(@RequestBody Merchant merchant) {

		return ResponseHandler.responseBuilder("Requested Merchant is registered successfully", HttpStatus.CREATED,
				service.registerMerchant(merchant));
	}

	@PostMapping("/merchant/payment")
	public ResponseEntity<Object> payment(@RequestBody PaymentInfo paymentInfo) {

		return ResponseHandler.responseBuilder("Payment Successfull", HttpStatus.ACCEPTED,
				service.payment(paymentInfo));
	}

	@GetMapping("/merchant/paymentstatus/{paymentId}")
	public ResponseEntity<Object> paymentStatus(@PathVariable int paymentId) {

		return ResponseHandler.responseBuilder("Your Payment Details", HttpStatus.FOUND,
				service.paymentStatus(paymentId));
	}
	
	@GetMapping("/merchant/paymentslist/{merchantName}")
	public ResponseEntity<Object> fetchPaymentListByMerchantPartialName(@PathVariable("merchantName") String merchantName)
	{
		return ResponseHandler.responseBuilder("Payment Details", HttpStatus.FOUND,
				service.fetchPaymentByMerchantName(merchantName));
	}
}
