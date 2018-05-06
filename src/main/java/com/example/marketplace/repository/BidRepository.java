package com.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.marketplace.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

}
