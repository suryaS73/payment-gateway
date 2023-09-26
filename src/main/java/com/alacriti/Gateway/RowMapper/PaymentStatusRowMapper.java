package com.alacriti.Gateway.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alacriti.Gateway.Entity.PaymentStatus;

public class PaymentStatusRowMapper implements RowMapper<PaymentStatus>{

	@Override
	public PaymentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentStatus status= new PaymentStatus();
		status.setId(rs.getInt("id"));
		status.setMerchantid(rs.getInt("merchantid"));
		status.setMerchantName(rs.getString("merchant_name"));
		status.setAmount(rs.getDouble("amount"));
		status.setStatus(rs.getString("status"));

		return status;
	}

}
