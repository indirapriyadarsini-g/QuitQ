package com.quitq.ECom.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Order;
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
		return cartRepository.getCartByCustomerId(customer.getId());
	
	}

	public Optional<Wishlist> getProductsFromWishlist(Customer customer) {
		return wishlistRepository.getProductByCustomerId(customer.getId());
	}

	public Optional<Order> customerOrder(Customer customer) {
		Optional<Cart> optCart = cartRepository.getCartByCustomerId(customer.getId());
//		Optional<Order> order = new Optional<Order>();
		if(optCart.isEmpty()) {
			return null;
		}
		
//		if()
		
		Cart cart = optCart.get();
		Order order = new Order();
		order.setCart(cart);
		order.setOrderAmount(cart.getCartTotal());
		
		int fee = 150;
		if(cart.getCartTotal()>500)	fee = 0;
		
		order.setOrderFee(fee);
		order.setOrderPlacedTime(LocalDateTime.now());
		order.setStatus(OrderStatus.ORDERED);
		
		
		return Optional.of(order);
	}

	
	
	
}
