package com.alacriti.Gateway.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alacriti.Gateway.Entity.Merchant;

public class MerchantRowMapper implements RowMapper<Merchant>{

	@Override
	public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Merchant merchant=new Merchant();
		merchant.setId(rs.getInt("id"));
		merchant.setName(rs.getString("name"));
		merchant.setEmail(rs.getString("email"));
		merchant.setPhone(rs.getString("phone"));
		merchant.setAccountbal(rs.getDouble("accountbal"));
		return merchant;
	}

}
