package com.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.marketplace.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long>{

}
