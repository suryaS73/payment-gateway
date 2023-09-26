package com.alacriti.Gateway.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alacriti.Gateway.Entity.CardsInfo;

public class CardsInfoRowMapper implements RowMapper<CardsInfo>{

	@Override
	public CardsInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CardsInfo card=new CardsInfo();
		card.setCardno(rs.getString("cardno"));
		card.setAvlbal(rs.getDouble("avlbal"));
		card.setValidity(rs.getDate("validity"));
		card.setId(rs.getInt("id"));

		return card;
	}
	

}
