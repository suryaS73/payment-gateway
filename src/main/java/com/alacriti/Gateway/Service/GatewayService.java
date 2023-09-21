package com.alacriti.Gateway.Service;

import org.springframework.http.ResponseEntity;

import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;

public interface GatewayService {

	String registerMerchant(Merchant merchant);
	
	String payment(PaymentInfo paymentInfo);
	
	String paymentStatus(int paymentId);
}
