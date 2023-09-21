package com.alacriti.Gateway.Service;

import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.MerchantDto;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;

public interface GatewayService {

	Merchant registerMerchant(Merchant merchant);
	
	PaymentStatus payment(PaymentInfo paymentInfo);
	
	PaymentStatus paymentStatus(int paymentId);

}
