package com.example.marketplace.controller;

import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Bid;
import com.example.marketplace.repository.BidRepository;
import com.example.marketplace.repository.BuyerRepository;
import com.example.marketplace.repository.ProjectRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BidController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BidController.class);

    @Autowired
    BidRepository bidRepository;
    
    @Autowired
    BuyerRepository buyerRepository;
    
    @Autowired
    ProjectRepository projectRepository;

    // Get All Bids for a Project
    @GetMapping("/projects/{id}/bids")
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }
    
    // Create a new Bid for a Project
    @PostMapping("/projects/{projectId}/bids")
    public Bid createBid(@PathVariable (value = "projectId") Long projectId, @RequestParam(value = "buyerId") Long buyerId, 
    					@Valid @RequestBody Bid bid) {   		
    	 		
      	bid.setProject(projectRepository.findById(projectId)
      			.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId)));
      		
        bid.setBuyer(buyerRepository.findById(buyerId)
        			.orElseThrow(() -> new ResourceNotFoundException("Buyer", "id", buyerId)));    		   			
                
       return bidRepository.save(bid);
    		
    }
    
    // Get a Single Bid
    @GetMapping("/bids/{id}")
    public Bid getBidById(@PathVariable(value = "id") Long bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", bidId));
    }
    
    // Update a Bid
    @PutMapping("/bids/{id}")
    public Bid updateBid(@PathVariable(value = "id") Long bidId,
                                            @Valid @RequestBody Bid bidDetails) {

        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", bidId));

        bid.setAmount(bidDetails.getAmount());
        return bidRepository.save(bid);
        
    }
    
    // Delete a Bid
    @DeleteMapping("/bids/{id}")
    public ResponseEntity<?> deleteBid(@PathVariable(value = "id") Long bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", bidId));

        bidRepository.delete(bid);

        return ResponseEntity.ok().build();
    }

}