package com.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.marketplace.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{

}
