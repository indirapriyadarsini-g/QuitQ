package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.model.Review;
import com.quitq.ECom.service.ReviewService;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins={"http://localhost:4200"})

public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@GetMapping("/getAll/{pid}")
	public ResponseEntity<?> getAllReview(@PathVariable int pid,Principal p,MessageDto dto)
	{
		String userName=p.getName();
		try {
			List<Review> list=reviewService.getAllReviews(userName,pid);
			return ResponseEntity.ok(list);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	

}
