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
		Merchant merchantFindByName = merchantRepo.findByName(merchant.getName());
		if (merchantFindByName == null) {
			merchantRepo.save(merchant);
			return merchant;
//			return "--- Merchant registered Successfully, Your Merchant Id : " + merchant.getId() + "---";
		} else {
//			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(merchantFindByName);
			return merchant;
		}

	}

	@Override
	public PaymentStatus payment(PaymentInfo paymentInfo) {

		// Fetching the Merchant is Registered with Gateway or no
		
		CardsInfo findByCardno = cardRepo.findByCardno(paymentInfo.getCardno());
		if (findByCardno != null) {
			double avlbal = findByCardno.getAvlbal();
			double paymentAmount = paymentInfo.getAmount();
		

			if (paymentInfo.getAmount() >= 1) {
				// Fetching the Merchant account bal in Gateway

				// Fetching CardDetails is Valid or Not
				Optional<Merchant> findMerchantById = merchantRepo.findById(paymentInfo.getMerchantid());
				if (findMerchantById.isPresent()) {

					Merchant merchant = findMerchantById.get();
					double merchantAccountBal=merchant.getAccountbal();
					// Checking the payment amount is avaliable in card or not
					if (paymentAmount <= avlbal) {

						// updating the user account balance
						findByCardno.setAvlbal(avlbal - paymentAmount);
						cardRepo.save(findByCardno);

						// updating Merchant account balance
						merchant.setAccountbal(merchantAccountBal + paymentAmount);
						merchantRepo.save(merchant);

						// Storing the payment details
						PaymentStatus paymentStatus = new PaymentStatus();
						paymentStatus.setMerchantid(paymentInfo.getMerchantid());
						paymentStatus.setAmount(paymentInfo.getAmount());
						paymentStatus.setStatus("SUCCESS");

						statusRepo.save(paymentStatus);

						return paymentStatus;
//						return "--- Payment Id : " + paymentStatus.getId() + ", Payment of " + paymentAmount
//								+ " is Successfully ---";
					} else {
						PaymentStatus paymentStatus = new PaymentStatus();
						paymentStatus.setMerchantid(paymentInfo.getMerchantid());
						paymentStatus.setAmount(paymentInfo.getAmount());
						paymentStatus.setStatus("DECLINED");
						statusRepo.save(paymentStatus);
						
						return paymentStatus;
//						return "--- Payment Id : " + paymentStatus.getId()
//								+ ", Payment Declined due to Insufficient Funds ---";
					}
				} else {
					return new PaymentStatus();
//					return "--- Merchant Not registered ---";
				}
			} else {
				return new PaymentStatus();
//				return "--- Enter Valid Amount (Greater than Rs.0) ---";
			}

		} else {
			return new PaymentStatus();
//			return "--- Card Details Not Found ---";
		}

	}

	@Override
	public PaymentStatus paymentStatus(int paymentId) {
		Optional<PaymentStatus> findById = statusRepo.findById(paymentId);
		if (findById.isPresent()) {
			PaymentStatus paymentStatus = findById.get();
			return paymentStatus;
//			return "--- Payment of  '" + paymentStatus.getAmount() + "'  is " + paymentStatus.getStatus()+" ---";
		} else {
			return null;
//			return "--- Invalid PaymentId ---";
		}
	}

}
