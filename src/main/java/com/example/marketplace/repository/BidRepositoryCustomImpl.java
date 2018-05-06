package com.example.marketplace.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.marketplace.controller.ProjectController;
import com.example.marketplace.model.Bid;

@Repository
@Transactional
public class BidRepositoryCustomImpl implements BidRepositoryCustom {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);	
	
	@PersistenceContext
    EntityManager entityManager;

	@Override
	public Object setWinningBidAutomatically(Long projectId) {
		LOGGER.info("Query begins");					
	
		Bid bid = getLowestBidObjectByProjectId(projectId);
		Long bidId = bid.getId();
		
		LOGGER.info("The bid with the minimun amount for current project is: " + bidId);
		
		int result = entityManager.createQuery("UPDATE Bid b SET b.hasWinned = true WHERE b.id = :bidId")
				.setParameter("bidId", bidId).executeUpdate();
		
		if (result == 1) {
			LOGGER.info("Query ends with success!");
		}
		else {
			LOGGER.info("Query ends with failure!");
		}
        
		return null;
	}
	
	public Bid getLowestBidObjectByProjectId(Long projectId) {
		TypedQuery<Bid> query = entityManager.createQuery("SELECT b FROM Bid b WHERE b.project.id = :projectId ORDER BY amount ASC", Bid.class);
		Bid bid = query.setParameter("projectId", projectId).setFirstResult(0).setMaxResults(1).getSingleResult();
		
		return bid;
	}

	@Override
	public float getLowestBidAmountByProjectId(Long projectId) {
		
		Bid bid = getLowestBidObjectByProjectId(projectId);
		float bidAmount = bid.getAmount();
		
		return bidAmount;
	}

}
