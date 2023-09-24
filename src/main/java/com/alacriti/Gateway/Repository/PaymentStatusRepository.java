package com.alacriti.Gateway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alacriti.Gateway.Entity.PaymentStatus;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer>{

	@Query("SELECT p FROM PaymentStatus p WHERE p.merchantName LIKE %:merchantName%")
	List<PaymentStatus> findByMerchantPartialName(@Param("merchantName") String merchantName);
}
