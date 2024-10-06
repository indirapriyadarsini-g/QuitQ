
package com.quitq.ECom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.dto.CartProductDto;
import com.quitq.ECom.dto.ProductWImageDto;
import com.quitq.ECom.dto.WishlistProductDto;
import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.ImageRepository;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.OrderRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.WishlistProductRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartProductRepository cartProductRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private WishlistProductRepository wishlistProductRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	

	public Customer register(Customer customer) {
			return customerRepository.save(customer);
	}

	public Optional<List<CartProduct>> getCartProductsByUsername(String username) {
		return cartProductRepository.getCartProductByUsername(username);
	
	}
	
	public Optional<List<WishlistProduct>> getWishlistProductByUsername(String username) {
		Customer customer = customerRepository.getCustomerByUsername(username);
		return wishlistProductRepository.getWishlistProductByCustomer(customer);
	}

	public Optional<Order> customerOrder(Cart cart) {
		
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

	public Optional<List<CartProduct>> getCartProductByUsername(String username) {
		return cartProductRepository.getCartProductByUsername(username);
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
	
	public ResponseEntity<?> decrementProductCountInOrder(OrderProduct orderProduct) {
		try {
			int n = orderProductRepository.subProductCount(orderProduct);
			if(n<1)	throw new Exception("No update happened");
			Optional<OrderProduct> op = orderProductRepository.findById(orderProduct.getId());
			return ResponseEntity.ok(op.get());
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

	public ResponseEntity<?> decrementProductCountInCart(CartProduct cartProduct) {
		try {
			int n = cartProductRepository.subProductCount(cartProduct);
			if(n<1)	throw new Exception("No update happened");
			Optional<CartProduct> op = cartProductRepository.findById(cartProduct.getId());
			return ResponseEntity.ok(op.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}

	public List<CartProductDto> getCartProductDtoByUsername(String name) {
		List<CartProductDto> cartProdDtoList = new ArrayList<>();
		List<CartProduct> cartProdList = cartProductRepository.getCartProductByUsername(name).get();
		for(CartProduct cp: cartProdList) {
			CartProductDto cpdto = new CartProductDto();
			cpdto.setCartProduct(cp);
			
			Product prod = cartProductRepository.getProductByCartProduct(cp);
			List<Image> imList = imageRepository.getImageByProduct(prod);
			cpdto.setImList(imList);
			cartProdDtoList.add(cpdto);
		}
		return cartProdDtoList;
	}

	public List<WishlistProductDto> getWishlistProductDtoByUsername(String name) {
		List<WishlistProductDto> wishlistProdDtoList = new ArrayList<>();
		List<WishlistProduct> wishlistProdList = wishlistProductRepository.getWishlistProductByUsername(name);
		for(WishlistProduct wp:wishlistProdList) {
			WishlistProductDto wlpdto = new WishlistProductDto();
			wlpdto.setWishlistProduct(wp);
			
			Product prod = wishlistProductRepository.getProductByWishlistProduct(wp);
			List<Image> imList = imageRepository.getImageByProduct(prod);
			wlpdto.setImList(imList);
			
			wishlistProdDtoList.add(wlpdto);
		}
		return wishlistProdDtoList;
	}

	public List<ProductWImageDto> getAllProduct() {
		List<Product> prod = productRepository.findAll();
		List<ProductWImageDto> prodWImage = new ArrayList<>();
		for(Product p: prod) {
			ProductWImageDto pdto = new ProductWImageDto();
			pdto.setProduct(p);
			List<Image> imList = imageRepository.getImageByProduct(p);
			pdto.setImageList(imList);
		}
		return prodWImage;
			
	}

	public List<Order> getOrderList(String name) {
		Optional<List<Order>> orderList = orderRepository.getOrderByUsername(name);
		return orderList.get();
	}

	public OrderProduct getOrderProductDetails(Order order) {
		// TODO Auto-generated method stub
		Optional<OrderProduct> orderProduct = orderProductRepository.getOrderProductByOrder(order);
		return orderProduct.get();
	}

	

	public List<Product> searchProdByParam(String category, int minDiscount, String prodName,String includeOutOfStock) {
		// TODO Auto-generated method stub
		List<Product> prodList = productRepository.findAll();
		if(!category.equals("none")) {
			prodList = prodList.stream().filter(p-> p.getC().toString().equalsIgnoreCase(category)).toList();
		}
		if(minDiscount!=0) {
			prodList = prodList.stream().filter(p-> p.getDiscount()>=minDiscount).toList();
		}
		if(!prodName.equals("none")) {
			prodList = prodList.stream().filter(p-> p.getTitle().equalsIgnoreCase(prodName)).toList();
		}
		if(!includeOutOfStock.equals("no")) {
			prodList = prodList.stream().filter(p-> p.getQuantity()>=0).toList();
		}
		return null;
	}

	
	
	
}
