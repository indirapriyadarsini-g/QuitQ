
package com.quitq.ECom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.dto.CartProductDto;
import com.quitq.ECom.dto.OrderProductWImageDto;
import com.quitq.ECom.dto.OrderSummaryDto;
import com.quitq.ECom.dto.ProductWImageDto;
import com.quitq.ECom.dto.WishlistProductDto;
import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.CustomerAddress;
import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Review;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.AddressRepository;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerAddressRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.ImageRepository;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.OrderRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.ReviewRepository;
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
	private CartRepository cartRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@Autowired
	private CustomerAddressRepository customerAddressRepository;

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private AddressRepository addressRepository;

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

	public OrderSummaryDto customerOrder(Cart cart) {
		
		List<CartProduct> cpList = cartProductRepository.getCartProductsByCart(cart).get();
		
		Order order = new Order();
		order.setOrderPlacedTime(LocalDateTime.now());
		orderRepository.save(order);
		
		List<OrderProductWImageDto> opwiList = new ArrayList<>(); 
		
		for(CartProduct cp: cpList) {
			
			OrderProductWImageDto opwi = new OrderProductWImageDto();
			
			OrderProduct op = new OrderProduct();
			op.setOrder(order);
			op.setProduct(cp.getProduct());
			op.setAmountPayable(cp.getAmountPayable());
			op.setDiscount(cp.getProductDiscount());
			op.setTotalAmount(cp.getProductTotalAmount());
			op.setQuantity(cp.getProductQuantity());
			
			orderProductRepository.save(op);
			
			opwi.setOrderProduct(op);
			List<Image> imList = imageRepository.getImageByProduct(op.getProduct());
			opwi.setImageList(imList);
			
			order.setOrderAmount(order.getOrderAmount()+op.getAmountPayable());
			order.setOrderDiscount(order.getOrderDiscount()+op.getDiscount());		
			
			orderRepository.save(order);
			
			opwiList.add(opwi);
		}
		
		int fee = 100;
		if(order.getOrderAmount()>500) fee=0;
		
		order.setOrderFee(fee);
		order.setOrderPlacedTime(LocalDateTime.now());
		order.setStatus(OrderStatus.ORDERED);
		
		orderRepository.save(order);
		

		cartProductRepository.deleteCartProductsByCart(cart);
		cart.setCartQuantity(0);
		cart.setCartTotal(0);
		cart.setProductList(null);
		
		cartRepository.save(cart);
		
		OrderSummaryDto odto = new OrderSummaryDto();
		odto.setOpwiList(opwiList);
		odto.setOrder(order);
		
		return odto;
	}

	public Optional<List<CartProduct>> getCartProductByUsername(String username) {
		return cartProductRepository.getCartProductByUsername(username);
	}

	public ResponseEntity<?> incrementProductCountInCart(CartProduct cartProduct) {
		try {
			int n = cartProductRepository.addProductCount(cartProduct);
			if(n<1)	throw new Exception("No update happened");
			
			Cart cart = cartProductRepository.getCartByCartProduct(cartProduct);
			cart.setCartQuantity(cart.getCartQuantity()+1);
			cart.setCartTotal(cart.getCartTotal()+cartProduct.getAmountPayable());
			cartRepository.save(cart);
			
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
			
			Order order = orderProductRepository.getOrderByOrderProduct(orderProduct);
			order.setOrderAmount(order.getOrderAmount()-orderProduct.getAmountPayable());
			if(order.getOrderAmount()<500) order.setOrderFee(100);
			orderRepository.save(order);
			
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
		
		Order order = orderProductRepository.getOrderByOrderProduct(orderProduct);
		order.setOrderAmount(order.getOrderAmount()+orderProduct.getAmountPayable());
		if(order.getOrderAmount()>500) order.setOrderFee(0);
		orderRepository.save(order);
		
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
			
			Cart cart = cartProductRepository.getCartByCartProduct(cartProduct);
			cart.setCartQuantity(cart.getCartQuantity()-1);
			cart.setCartTotal(cart.getCartTotal()+cartProduct.getAmountPayable());
			cartRepository.save(cart);
			
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
			
			prodWImage.add(pdto);
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
		if(!category.equals("none") && !category.equals("") && category!=null) {
			prodList = prodList.stream().filter(p-> p.getC().toString().equalsIgnoreCase(category)).toList();
		}
		if(minDiscount!=0) {
			prodList = prodList.stream().filter(p-> p.getDiscount()>=minDiscount).toList();
		}
		if(!prodName.equals("none") && !prodName.equals("") && prodName!=null) {
			prodList = prodList.stream().filter(p-> p.getTitle().equalsIgnoreCase(prodName)).toList();
		}
		if(!includeOutOfStock.equals("no") && !includeOutOfStock.equals("") && includeOutOfStock!=null) {
			prodList = prodList.stream().filter(p-> p.getQuantity()>=0).toList();
		}
		return prodList;
	}

	public void addAddressByUsername(Address address, String name) {
		// TODO Auto-generated method stub
		
		addressRepository.save(address);
		CustomerAddress customerAddress = new CustomerAddress();
		Customer customer = customerRepository.getCustomerByUsername(name);
		customerAddress.setAddress(address);
		customerAddress.setCustomer(customer);
		customerAddressRepository.save(customerAddress);
	}

	public List<Address> getAddressByUsername(String name) {
		// TODO Auto-generated method stub
		return customerAddressRepository.getAddressByUsername(name);
	}


	public List<Review> getProductReviews(Product product) {
		// TODO Auto-generated method stub
		return reviewRepository.getReviewByProduct(product);
	}

	public OrderProduct getOrderProductDetailsByOrderId(int oId) {
		// TODO Auto-generated method stub
		return orderProductRepository.getOrderProductByOrderId(oId);
	}

	public void addReview(Product p, OrderProduct op, Review review, String name) {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.getCustomerByUsername(name);
		review.setProduct(p);
		review.setCustomer(customer);
		reviewRepository.save(review);
		op.setReview(review);
		orderProductRepository.save(op);
	}

	public List<OrderProduct> getOrderProductList(String name) {
		// TODO Auto-generated method stub
		List<OrderProduct> opList = orderProductRepository.getOrderProductByUsername(name);
		return opList;
	}

	

	
	
	
}
