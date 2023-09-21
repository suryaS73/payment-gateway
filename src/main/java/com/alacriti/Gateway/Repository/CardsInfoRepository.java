package com.alacriti.Gateway.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alacriti.Gateway.Entity.CardsInfo;

@Repository
public interface CardsInfoRepository extends JpaRepository<CardsInfo, Integer>{

	CardsInfo findByCardno(String cardno);
}
