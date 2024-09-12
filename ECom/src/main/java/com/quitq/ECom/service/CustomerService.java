package com.quitq.ECom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.WishlistRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private WishlistRepository wishlistRepository;

	public Boolean login(User user) {
		Optional<User> loginuser = userRepository.findById(user.getId());
		if(loginuser==null)	return false;
		else	
			ResponseEntity.ok("User logged in");
		return true;
	}

	public Boolean register(Customer customer) {
		try{
			customerRepository.save(customer);
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
		
	}

	public Optional<Cart> getProductsFromCart(Customer customer) {
		return cartRepository.getProductByCustomerId(customer.getId());
	
	}

	public Optional<Wishlist> getProductsFromWishlist(Customer customer) {
		return wishlistRepository.getProductByCustomerId(customer.getId());
	}
	
	
}
