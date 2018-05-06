package com.example.marketplace.repository;

public interface BidRepositoryCustom {

	Object setWinningBidAutomatically(Long projectId);
	
	float getLowestBidAmountByProjectId(Long projectId);

}
