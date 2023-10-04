package com.alacriti.Gateway.Service;

import java.util.List;

import com.alacriti.Gateway.Entity.CardsInfo;
import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentDetails;
import com.alacriti.Gateway.Entity.PaymentInfo;

public interface GatewayService {

	Merchant merchantRegistrationService(Merchant merchant);
	
	Merchant findMerchantByName(String name);
	
	Merchant findMerchatnByMerchantId(int merchatnId);
	
	Merchant findMerchatnByEmail(String email);
	
	CardsInfo findCardDetailsByCardNO(String cardNo);
	
	PaymentDetails payment(PaymentInfo paymentInfo);
	
	PaymentDetails processThePayment(CardsInfo cardDetails, Merchant merchant, double paymentAmount);

	PaymentDetails insertIntoPaymentsDetails(Merchant merchant,double paymentAmount,String cardNo,String paymentStatus);
	
	int updateCardsInfoAcountBalance(String cardNo,double paymentAmount,double avaliableBalance);
	
	int updateMerchantAcountBalance(int merchantId,double paymentAmount,double avaliableBalance);

	PaymentDetails getPaymentDetailsByPaymentId(int paymentId);

	
	List<PaymentDetails> fetchPaymentsByMerchantName(String merchantName);

}
