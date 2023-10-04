package com.alacriti.Gateway.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.alacriti.Gateway.Entity.CardsInfo;
import com.alacriti.Gateway.Entity.Merchant;
import com.alacriti.Gateway.Entity.PaymentDetails;
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.RowMapper.CardsInfoRowMapper;
import com.alacriti.Gateway.RowMapper.MerchantRowMapper;
import com.alacriti.Gateway.RowMapper.PaymentDetailsRowMapper;
import com.alacriti.Gateway.exception.InsufficientAmountException;
import com.alacriti.Gateway.exception.InvalidCardDetailsException;
import com.alacriti.Gateway.exception.MerchantAlreadyRegisteredException;
import com.alacriti.Gateway.exception.MerchantNotFoundException;
import com.alacriti.Gateway.exception.PaymentIdNotFoundException;

@Service
public class GatewayServiceImp implements GatewayService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// Merchant Registration Method
	@Override
	public Merchant merchantRegistrationService(Merchant merchant) {

		Merchant checkMerchantIsAlreadyPresentOrNot = findMerchatnByEmail(merchant.getEmail());
		if (checkMerchantIsAlreadyPresentOrNot == null) {
			String query1 = "insert into merchant(name,email,phone,accountbal) values(?,?,?,?)";
			jdbcTemplate.update(query1, merchant.getName(), merchant.getEmail(), merchant.getPhone(),
					merchant.getAccountbal());

			return findMerchatnByEmail(merchant.getEmail());

		}
		throw new MerchantAlreadyRegisteredException(
				"Merchant You entered is already Registered with this Email, with merchantId : "
						+ checkMerchantIsAlreadyPresentOrNot.getId());

	}

	// Make Payment
	@Override
	public PaymentDetails payment(PaymentInfo paymentInfo) {

		Merchant findMerchatnByMerchantId = findMerchatnByMerchantId(paymentInfo.getMerchantid());
		if (findMerchatnByMerchantId != null) {
			CardsInfo findCardDetailsByCardNO = findCardDetailsByCardNO(paymentInfo.getCardno());
			if (findCardDetailsByCardNO != null) {
				return processThePayment(findCardDetailsByCardNO, findMerchatnByMerchantId, paymentInfo.getAmount());
			}
		}
		return null;

	}

	/**
	 * This method gets the payment details by payment id
	 * @param paymentId takes the payment id 
	 * @return Payment details
	 * 
	 * **/
	// To check payment status with paymentId
	@Override
	public PaymentDetails getPaymentDetailsByPaymentId(int paymentId) {

		String queryToGetPaymentStatus = "select * from paymentsdetails where id=?";
		RowMapper<PaymentDetails> rm2 = new PaymentDetailsRowMapper();
		List<PaymentDetails> list2 = jdbcTemplate.query(queryToGetPaymentStatus, rm2, paymentId);

		if (list2.isEmpty()) {
			throw new PaymentIdNotFoundException("Payment Id NOT_FOUND Or Invalid");
		}

		return list2.get(0);
	}

	// To get all the payments done the Merchant with MerchantName
	@Override
	public List<PaymentDetails> fetchPaymentsByMerchantName(String merchantName) {

		String queryToGetPaymentStatus = "select * from paymentsdetails where merchant_name like ? ";
		String pattern = "%" + merchantName + "%";
		RowMapper<PaymentDetails> rm2 = new PaymentDetailsRowMapper();
		List<PaymentDetails> list2 = jdbcTemplate.query(queryToGetPaymentStatus, rm2, pattern);
		if (list2.isEmpty()) {
			throw new PaymentIdNotFoundException("No Payments Found");
		}
		return list2;
	}

	// Helper method for MerchantRegistration to check Merchant is already
	// registered or not
	public Merchant findMerchantByName(String name) {
		String query = "select * from merchant where name =?";
		RowMapper<Merchant> rm = new MerchantRowMapper();
		List<Merchant> list = jdbcTemplate.query(query, rm, name);

		if (list.size() <= 0) {
			return null;
		}
		return list.get(0);

	}

	public Merchant findMerchatnByMerchantId(int merchantId) {
		String query = "select * from merchant where id =?";
		RowMapper<Merchant> rm = new MerchantRowMapper();
		List<Merchant> list = jdbcTemplate.query(query, rm, merchantId);
		if (list.isEmpty()) {
			throw new MerchantNotFoundException("Merchant Not Found");
		}
		return list.get(0);
	}

	@Override
	public Merchant findMerchatnByEmail(String email) {
		String query = "select * from merchant where email =?";
		RowMapper<Merchant> rm = new MerchantRowMapper();
		List<Merchant> list = jdbcTemplate.query(query, rm, email);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public CardsInfo findCardDetailsByCardNO(String cardNo) {
		String query = "select * from cardsinfo where cardno =?";
		RowMapper<CardsInfo> rm = new CardsInfoRowMapper();
		List<CardsInfo> list = jdbcTemplate.query(query, rm, cardNo);
		if (list.isEmpty()) {
			throw new InvalidCardDetailsException("Card Details Invalid Or Not Found");
		}
		return list.get(0);

	}

	public PaymentDetails processThePayment(CardsInfo cardDetails, Merchant merchant, double paymentAmount) {
		double cardAvalibleBalance = cardDetails.getAvlbal();

		if (paymentAmount >= cardAvalibleBalance) {

			PaymentDetails insertIntoPaymentsData = insertIntoPaymentsDetails(merchant, paymentAmount,
					cardDetails.getCardno(),"PAYMENT DECLINED");

			throw new InsufficientAmountException(
					"Payment declined due to Insufficient Account Balance. Payment Id :" + insertIntoPaymentsData.getId());
		}

		updateCardsInfoAcountBalance(cardDetails.getCardno(), paymentAmount, cardAvalibleBalance);

		updateMerchantAcountBalance(merchant.getId(), paymentAmount, merchant.getAccountbal());

		return insertIntoPaymentsDetails(merchant, paymentAmount, cardDetails.getCardno(),"PAYMENT SUCCESSFULL");

	}

	@Override
	public int updateCardsInfoAcountBalance(String cardNo, double paymentAmount, double avaliableBalance) {
		String queryForCardInfo = "update  cardsinfo set avlbal=? where cardno=?";

		return jdbcTemplate.update(queryForCardInfo, avaliableBalance - paymentAmount, cardNo);

	}

	@Override
	public int updateMerchantAcountBalance(int merchantId, double paymentAmount, double avaliableBalance) {
		String queryForMerchantAmountUpdate = "update  merchant set accountbal=? where id=?";

		return jdbcTemplate.update(queryForMerchantAmountUpdate, avaliableBalance + paymentAmount, merchantId);

	}

	public PaymentDetails insertIntoPaymentsDetails(Merchant merchant, double paymentAmount, String cardNo,String paymentStatus) {
		String queryForPaymentUpdate = "insert into paymentsdetails(amount,merchantid,status,merchant_name,cardno) values(?,?,?,?,?)";
		int paymentId;

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(queryForPaymentUpdate, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, paymentAmount);
			ps.setInt(2, merchant.getId());
			ps.setString(3, paymentStatus);
			ps.setString(4, merchant.getName());
			ps.setString(5, cardNo);
			return ps;
		}, keyHolder);

		paymentId = keyHolder.getKey().intValue();

		return getPaymentDetailsByPaymentId(paymentId);
	}

}
