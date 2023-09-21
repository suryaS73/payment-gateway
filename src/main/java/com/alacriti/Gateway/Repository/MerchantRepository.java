package com.alacriti.Gateway.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alacriti.Gateway.Entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer>{

	Merchant findByName(String name);
}
