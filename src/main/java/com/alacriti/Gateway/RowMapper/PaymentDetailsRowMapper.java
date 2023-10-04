package com.alacriti.Gateway.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alacriti.Gateway.Entity.PaymentDetails;

public class PaymentDetailsRowMapper implements RowMapper<PaymentDetails>{

	@Override
	public PaymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentDetails status= new PaymentDetails();
		status.setId(rs.getInt("id"));
		status.setMerchantid(rs.getInt("merchantid"));
		status.setMerchantName(rs.getString("merchant_name"));
		status.setAmount(rs.getDouble("amount"));
		status.setStatus(rs.getString("status"));
		status.setCardNo(rs.getString("cardno"));
		return status;
	}

}
