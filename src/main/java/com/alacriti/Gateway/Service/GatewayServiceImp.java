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
import com.alacriti.Gateway.Entity.PaymentInfo;
import com.alacriti.Gateway.Entity.PaymentStatus;
import com.alacriti.Gateway.RowMapper.CardsInfoRowMapper;
//import com.alacriti.Gateway.Repository.CardsInfoRepository;
//import com.alacriti.Gateway.Repository.MerchantRepository;
//import com.alacriti.Gateway.Repository.PaymentStatusRepository;
import com.alacriti.Gateway.RowMapper.MerchantRowMapper;
import com.alacriti.Gateway.RowMapper.PaymentStatusRowMapper;
import com.alacriti.Gateway.exception.InsufficientAmountException;
import com.alacriti.Gateway.exception.InvalidCardDetailsException;
import com.alacriti.Gateway.exception.MerchantAlreadyRegisteredException;
import com.alacriti.Gateway.exception.MerchantNotFoundException;
import com.alacriti.Gateway.exception.PaymentIdNotFoundException;

@Service
public class GatewayServiceImp implements GatewayService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Merchant registerMerchant(Merchant merchant) {

		return checkMerchantIsAlreadyPresentOrNot(merchant);

	}

	public Merchant checkMerchantIsAlreadyPresentOrNot(Merchant merchant) {
		String query = "select * from merchant where name =?";
		RowMapper<Merchant> rm = new MerchantRowMapper();
		List<Merchant> list = jdbcTemplate.query(query, rm, merchant.getName());

		if (list.size() <= 0) {
			String query1 = "insert into merchant(name,email,phone,accountbal) values(?,?,?,?)";
			int update = jdbcTemplate.update(query1, merchant.getName(), merchant.getEmail(), merchant.getPhone(),
					merchant.getAccountbal());

			String queryGet = "select * from merchant where name=?";
			RowMapper<Merchant> rm2 = new MerchantRowMapper();
			List<Merchant> list2 = jdbcTemplate.query(queryGet, rm2, merchant.getName());

			return list2.get(0);
		}

		throw new MerchantAlreadyRegisteredException(
				"Merchant You entered is already Registered  with merchantId : " + list.get(0).getId(), list.get(0));

	}

	@Override
	public PaymentStatus payment(PaymentInfo paymentInfo) {

		return checkPaymentAmountIsValidOrNot(paymentInfo);

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

	public PaymentStatus checkMerchantIsRegisteredOrNot(PaymentInfo paymentInfo) {
		String query = "select * from merchant where id =?";
		RowMapper<Merchant> rm = new MerchantRowMapper();
		List<Merchant> list = jdbcTemplate.query(query, rm, paymentInfo.getMerchantid());
		if (list.isEmpty()) {
			throw new MerchantNotFoundException("Merchant Not Found");
		}
		return checkCardDetailsIsValidOrNot(paymentInfo, list.get(0));
	}

	public PaymentStatus checkCardDetailsIsValidOrNot(PaymentInfo paymentInfo, Merchant merchant) {
		String query = "select * from cardsinfo where cardno =?";
		RowMapper<CardsInfo> rm = new CardsInfoRowMapper();
		List<CardsInfo> list = jdbcTemplate.query(query, rm, paymentInfo.getCardno());
		if (list.isEmpty()) {
			throw new InvalidCardDetailsException("Card Details Invalid Or Not Found");
		}
		return updatePaymentStatus(list.get(0), merchant, paymentInfo.getAmount());

	}

	public PaymentStatus updatePaymentStatus(CardsInfo cardDetails, Merchant merchant, double paymentAmount) {
		double avalibleBalance = cardDetails.getAvlbal();

		String queryForPaymentUpdate = "insert into paymentstatus(amount,merchantid,status,merchant_name) values(?,?,?,?)";

		if (paymentAmount >= avalibleBalance) {

			int update = jdbcTemplate.update(queryForPaymentUpdate, paymentAmount, merchant.getId(),
					"Payment DECLINED due to Insufficient Balance", merchant.getName());
			throw new InsufficientAmountException("Insufficient Account Balance");
		}

		String queryForCardInfo = "update  cardsinfo set avlbal=? where cardno=?";

		jdbcTemplate.update(queryForCardInfo, avalibleBalance - paymentAmount, cardDetails.getCardno());

		String queryForMerchantAmountUpdate = "update  merchant set accountbal=? where id=?";

		jdbcTemplate.update(queryForMerchantAmountUpdate, merchant.getAccountbal() + paymentAmount, merchant.getId());

		int paymentId;

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(queryForPaymentUpdate, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, paymentAmount);
			ps.setInt(2, merchant.getId());
			ps.setString(3, "Payment SUCCESSFULL");
			ps.setString(4, merchant.getName());
			return ps;
		}, keyHolder);

		paymentId = keyHolder.getKey().intValue();

		String queryToGetPaymentStatus = "select * from paymentstatus where id=?";
		RowMapper<PaymentStatus> rm2 = new PaymentStatusRowMapper();
		List<PaymentStatus> list2 = jdbcTemplate.query(queryToGetPaymentStatus, rm2, paymentId);

		return list2.get(0);

	}

	@Override
	public PaymentStatus paymentStatus(int paymentId) {

		String queryToGetPaymentStatus = "select * from paymentstatus where id=?";
		RowMapper<PaymentStatus> rm2 = new PaymentStatusRowMapper();
		List<PaymentStatus> list2 = jdbcTemplate.query(queryToGetPaymentStatus, rm2, paymentId);

		if (list2.isEmpty()) {
			throw new PaymentIdNotFoundException("Payment Id NOT_FOUND Or Invalid");
		}

		return list2.get(0);
	}

	@Override
	public List<PaymentStatus> fetchPaymentByMerchantName(String merchantName) {

		String queryToGetPaymentStatus = "select * from paymentstatus where merchant_name like ? ";
		String pattern = "%" + merchantName + "%";
		RowMapper<PaymentStatus> rm2 = new PaymentStatusRowMapper();
		List<PaymentStatus> list2 = jdbcTemplate.query(queryToGetPaymentStatus, rm2, pattern);
		if (list2.isEmpty()) {
			throw new PaymentIdNotFoundException("No Payments Found");
		}
		return list2;
	}

}
