package com.quitq.ECom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.model.Review;
import com.quitq.ECom.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	public List<Review> getAllReviews(String userName,int pid) throws InvalidIdException
	{
		List<Review> list=reviewRepository.getAllReviewOfProduct(userName, pid);
		if(list.isEmpty())
		{
			throw new InvalidIdException("No reviews");
		}
		return list;
			
	}
	

}
