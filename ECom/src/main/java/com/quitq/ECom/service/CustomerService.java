
package com.quitq.ECom.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.WishlistRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartProductRepository cartProductRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	

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
		return cartRepository.getCartByCustomer(customer);
	
	}
	
	public Optional<Wishlist> getProductsFromWishlist(String username) {
		Customer customer = customerRepository.getCustomerByUsername(username);
		return wishlistRepository.getWishlistByCustomer(customer);
	}

	public Optional<Order> customerOrder(Customer customer,Cart cart) {
		
		Order order = new Order();
		
		order.setCart(cart);
		order.setOrderAmount(cart.getCartTotal());
		
		int fee = 100;
		if(cart.getCartTotal()>500)	fee = 0;
		
		order.setOrderFee(fee);
		order.setOrderPlacedTime(LocalDateTime.now());
		order.setStatus(OrderStatus.ORDERED);
		

		cartProductRepository.deleteCartProductsByCart(cart);
		
		return Optional.of(order);
	}

	public Optional<Cart> getCartByCustomer(Customer customer) {
		return cartRepository.getCartByCustomer(customer);
	}

	public ResponseEntity<?> incrementProductCountInCart(CartProduct cartProduct) {
		try {
			int n = cartProductRepository.addProductCount(cartProduct);
			if(n<1)	throw new Exception("No update happened");
			Optional<CartProduct> cp = cartProductRepository.findById(cartProduct.getId());
			return ResponseEntity.ok(cp.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}			
	}
	

	public ResponseEntity<?> incrementProductCountInOrder(OrderProduct orderProduct) {
	try {
		int n = orderProductRepository.addProductCount(orderProduct);
		if(n<1)	throw new Exception("No update happened");
		Optional<OrderProduct> op = orderProductRepository.findById(orderProduct.getId());
		return ResponseEntity.ok(op.get());
	} catch (Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}			
}

	public Customer getProfileDetails(String name) {
		Customer customer = customerRepository.getCustomerByUsername(name);
		return customer;
	}

	
	
	
}
