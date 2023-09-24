package com.alacriti.Gateway.Service;

import java.sql.SQLRecoverableException;
import java.util.List;
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
import com.alacriti.Gateway.exception.InsufficientAmountException;
import com.alacriti.Gateway.exception.InvalidCardDetailsException;
import com.alacriti.Gateway.exception.MerchantAlreadyRegisteredException;
import com.alacriti.Gateway.exception.MerchantNotFoundException;
import com.alacriti.Gateway.exception.PaymentIdNotFoundException;

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
		if (merchantRepo.findByName(merchant.getName()) != null) {
			throw new MerchantAlreadyRegisteredException("Merchant You entered is already Registered");
		}
		return merchantRepo.save(merchant);

	}

	private Exception SQLException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentStatus payment(PaymentInfo paymentInfo) {

		return checkPaymentAmountIsValidOrNot(paymentInfo);

	}

	public PaymentStatus checkMerchantIsRegisteredOrNot(PaymentInfo paymentInfo) {
		Optional<Merchant> findMerchantById = merchantRepo.findById(paymentInfo.getMerchantid());

		if (findMerchantById.isEmpty()) {
			throw new MerchantNotFoundException("Merchant Not Found");
		}
		return checkCardDetailsIsValidOrNot(paymentInfo, findMerchantById.get());
	}

	public PaymentStatus checkPaymentAmountIsValidOrNot(PaymentInfo paymentInfo) {
		if (paymentInfo.getAmount() >= 1) {
			return checkMerchantIsRegisteredOrNot(paymentInfo);

		} else {
			PaymentStatus status = new PaymentStatus();
			status.setStatus("Enter Valid Amount (Greater Than 1)");
			return status;
		}
	}

	public PaymentStatus checkCardDetailsIsValidOrNot(PaymentInfo paymentInfo, Merchant merchant) {
		CardsInfo findByCardno = cardRepo.findByCardno(paymentInfo.getCardno());
		if (cardRepo.findByCardno(paymentInfo.getCardno()) == null) {
			throw new InvalidCardDetailsException("Card Details Invalid Or Not Found");
		}
		return updatePaymentStatus(findByCardno, merchant, paymentInfo.getAmount());

	}

	public PaymentStatus updatePaymentStatus(CardsInfo cardDetails, Merchant merchant, double paymentAmount) {
		double avalibleBalance = cardDetails.getAvlbal();

		if (paymentAmount >= avalibleBalance) {
			PaymentStatus paymentStatus = new PaymentStatus();
			paymentStatus.setMerchantid(merchant.getId());
			paymentStatus.setMerchantName(merchant.getName());
			paymentStatus.setAmount(paymentAmount);
			paymentStatus.setStatus("Payment DECLINED due to Insufficient Balance");
			statusRepo.save(paymentStatus);
			throw new InsufficientAmountException("Insufficient Account Balance");
		}

		cardDetails.setAvlbal(avalibleBalance - paymentAmount);
		cardRepo.save(cardDetails);

		merchant.setAccountbal(merchant.getAccountbal() + paymentAmount);
		merchantRepo.save(merchant);

		PaymentStatus paymentStatus = new PaymentStatus();
		paymentStatus.setMerchantid(merchant.getId());
		paymentStatus.setMerchantName(merchant.getName());
		paymentStatus.setAmount(paymentAmount);
		paymentStatus.setStatus("Payment SUCCESSFULL");

		statusRepo.save(paymentStatus);

		return paymentStatus;
	}

	@Override
	public PaymentStatus paymentStatus(int paymentId) {

		if (statusRepo.findById(paymentId).isEmpty()) {
			throw new PaymentIdNotFoundException("Payment Id NOT_FOUND Or Invalid");
		}
		return statusRepo.findById(paymentId).get();
	}

	@Override
	public List<PaymentStatus> fetchPaymentByMerchantName(String merchantName) {
		
		if (statusRepo.findByMerchantPartialName(merchantName).isEmpty()) {
			throw new PaymentIdNotFoundException("No Payments Found");
		}
		return statusRepo.findByMerchantPartialName(merchantName);
	}

}
