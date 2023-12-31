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
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;
import com.alacriti.Gateway.Service.GatewayServiceImp;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
	
	@Autowired
	private GatewayServiceImp service;

	@PostMapping("/merchant/registration")
	public String registerMerchant(@RequestBody Merchant merchant)
	{
		
		return service.registerMerchant(merchant);
	}
	
	@PostMapping("/merchant/payment")
	public String payment(@RequestBody PaymentInfo paymentInfo)
	{
		return service.payment(paymentInfo);
	}
	
	@GetMapping("/merchant/paymentstatus/{pId}")
	public String paymentStatus(@PathVariable("pId") int  pId)
	{
		
		return service.paymentStatus(pId);
//		if (paymentStatus!=null) {
//			return ResponseEntity.status(HttpStatus.FOUND).body(paymentStatus);
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(paymentStatus);
//		}
	}
}
