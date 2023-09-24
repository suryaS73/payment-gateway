package com.alacriti.Gateway.Service;

import java.util.List;

import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;

public interface GatewayService {

	Merchant registerMerchant(Merchant merchant);
	
	PaymentStatus payment(PaymentInfo paymentInfo);
	
	PaymentStatus paymentStatus(int paymentId);
	
	List<PaymentStatus> fetchPaymentByMerchantName(String merchantName);

}
