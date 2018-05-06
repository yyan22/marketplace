package com.example.marketplace.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplace.model.Buyer;
import com.example.marketplace.repository.BuyerRepository;

@RestController
@RequestMapping("/api")
public class BuyerController {

public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);	
	
    @Autowired
    BuyerRepository buyerRepository;
    
    // Create a new Buyer
    @PostMapping("/buyers")
    public Buyer createBuyer(@Valid @RequestBody Buyer buyer)  {   		
       return buyerRepository.save(buyer);   		
    }
}
