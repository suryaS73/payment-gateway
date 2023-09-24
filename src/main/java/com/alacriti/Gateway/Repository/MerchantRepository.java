package com.alacriti.Gateway.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.alacriti.Gateway.Entity.Merchant;


public interface MerchantRepository extends JpaRepository<Merchant, Integer>{

	@Query("SELECT merchant FROM Merchant merchant WHERE merchant.name = :merchantName")
	Merchant findByName(@Param("merchantName") String name);
	
}
