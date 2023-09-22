package com.alacriti.Gateway.Controller;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alacriti.Gateway.Entity.CardsInfo;
import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.MerchantDto;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;
import com.alacriti.Gateway.Service.GatewayServiceImp;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

	@Autowired
	private GatewayServiceImp service;

	@PostMapping("/merchant/registration")
	public ResponseEntity<Merchant> registerMerchant(@RequestBody Merchant merchant) {
		Merchant registerMerchant = service.registerMerchant(merchant);
		if (registerMerchant != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(registerMerchant);
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(merchant);
	}

	@PostMapping("/merchant/payment")
	public ResponseEntity<PaymentStatus> payment(@RequestBody PaymentInfo paymentInfo) {
		PaymentStatus payment = service.payment(paymentInfo);
		if (payment.getStatus() == "Payment SUCCESSFULL") {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(payment);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payment);
		}
	}

	@GetMapping("/merchant/paymentstatus/{pId}")
	public ResponseEntity<PaymentStatus> paymentStatus(@PathVariable("pId") int pId) {

		PaymentStatus paymentStatus = service.paymentStatus(pId);
		if (paymentStatus.getStatus().equals("Payment SUCCESSFULL")
				|| paymentStatus.getStatus().equals("Payment DECLINED due to Insufficient Balance")
				|| paymentStatus.getStatus().equals("SUCCESS")) {
			
			return ResponseEntity.status(HttpStatus.FOUND).body(paymentStatus);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(paymentStatus);
		}
	}
}
