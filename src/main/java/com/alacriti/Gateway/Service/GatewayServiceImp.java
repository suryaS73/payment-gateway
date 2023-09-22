package com.alacriti.Gateway.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alacriti.Gateway.Entity.CardsInfo;
import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;
import com.alacriti.Gateway.Repository.CardsInfoRepository;
import com.alacriti.Gateway.Repository.MerchantRepository;
import com.alacriti.Gateway.Repository.PaymentStatusRepository;

@Service
public class GatewayServiceImp implements GatewayService {

	@Autowired
	private MerchantRepository merchantRepo;

	@Autowired
	private CardsInfoRepository cardRepo;

	@Autowired
	private PaymentStatusRepository statusRepo;

	@Override
	public Merchant registerMerchant(Merchant merchant) {
		try {
			Merchant merchantFindByName = merchantRepo.findByName(merchant.getName());
			if (merchantFindByName == null) {
				merchantRepo.save(merchant);
				return merchant;
//				return "--- Merchant registered Successfully, Your Merchant Id : " + merchant.getId() + "---";
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public PaymentStatus payment(PaymentInfo paymentInfo) {

		return checkPaymentAmountIsValidOrNot(paymentInfo);

	}

//	public CardsInfo checkCardDetailsIsValidOrNot() {
//		
//	}

	public PaymentStatus checkMerchantIsRegisteredOrNot(PaymentInfo paymentInfo)
	{
		Optional<Merchant> findMerchantById = merchantRepo.findById(paymentInfo.getMerchantid());
		if (findMerchantById.isPresent()) {
			Merchant merchant = findMerchantById.get();
			return checkCardDetailsIsValidOrNot(paymentInfo, merchant);
		}
		else {
			PaymentStatus status =new PaymentStatus();
			status.setStatus("Merchant is Not Registered with gateway");
			return status;
		}
	}
	
	public PaymentStatus checkPaymentAmountIsValidOrNot(PaymentInfo paymentInfo)
	{
		if (paymentInfo.getAmount()>=1) {
			return checkMerchantIsRegisteredOrNot(paymentInfo);
			
		} else {
			PaymentStatus status =new PaymentStatus();
			status.setStatus("Enter Valid Amount (Greater Than 1)");
			return status;
		}
	}
	
	public PaymentStatus checkCardDetailsIsValidOrNot(PaymentInfo paymentInfo,Merchant merchant)
	{
		CardsInfo findByCardno = cardRepo.findByCardno(paymentInfo.getCardno());
		if (findByCardno!=null) {
			return updatePaymentStatus(findByCardno, merchant, paymentInfo.getAmount());
		} else {
			PaymentStatus status =new PaymentStatus();
			status.setStatus("Card Details Not Found");
			return status;
		}

	}
	
	public PaymentStatus updatePaymentStatus(CardsInfo cardDetails, Merchant merchant, double paymentAmount) {
		double avalibleBalance = cardDetails.getAvlbal();
		if (paymentAmount <= avalibleBalance) {

			cardDetails.setAvlbal(avalibleBalance - paymentAmount);
			cardRepo.save(cardDetails);

			merchant.setAccountbal(merchant.getAccountbal() + paymentAmount);
			merchantRepo.save(merchant);

			PaymentStatus paymentStatus = new PaymentStatus();
			paymentStatus.setMerchantid(merchant.getId());
			paymentStatus.setAmount(paymentAmount);
			paymentStatus.setStatus("Payment SUCCESSFULL");

			statusRepo.save(paymentStatus);

			return paymentStatus;

		}
		PaymentStatus paymentStatus = new PaymentStatus();
		paymentStatus.setMerchantid(merchant.getId());
		paymentStatus.setAmount(paymentAmount);
		paymentStatus.setStatus("Payment DECLINED due to Insufficient Balance");
		statusRepo.save(paymentStatus);

		return paymentStatus;
	}

	@Override
	public PaymentStatus paymentStatus(int paymentId) {
		Optional<PaymentStatus> findById = statusRepo.findById(paymentId);
		if (findById.isPresent()) {
			PaymentStatus paymentStatus = findById.get();
			return paymentStatus;
		} else {
			PaymentStatus status =new PaymentStatus();
			status.setStatus("Payment Not Found");
			return status;
		}
	}

}
