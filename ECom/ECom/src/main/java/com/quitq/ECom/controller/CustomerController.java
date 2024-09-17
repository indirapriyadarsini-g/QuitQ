package com.quitq.ECom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.dto.OrderInvoiceDto;
import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.enums.StatusType;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.WishlistRepository;
import com.quitq.ECom.service.CustomerService;
//import com.quitq.ECom.service.WarehouseManagerService;

@RestController
@RequestMapping("customer/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartProductRepository cartProductRepository;
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
//	@Autowired
//	private WarehouseManagerService warehouseManagerService;
	
	@PostMapping("/register")
	public ResponseEntity<?> customerRegister(@RequestBody Customer customer, MessageDto dto){
		try {
			
			customerService.register(customer);
			customer.getUser().setRole(RoleType.CUSTOMER);
			return ResponseEntity.ok(new String("Customer details registered"));
		} catch (Exception e) {
			dto.setMsg("Invalid credentials");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	
	@GetMapping("/my-cart")
	public ResponseEntity<?> getProductsFromCart(@RequestBody Customer customer,MessageDto dto){
		Optional<Cart> cart = cartRepository.getCartByCustomer(customer);
		Optional<List<CartProduct>> cartProdrepo = cartProductRepository.getCartProductsByCart(cart.get());
		
		if(cartProdrepo.isEmpty()) {
			dto.setMsg("No products inside cart");
			return ResponseEntity.badRequest().body(dto);
		}

		List<CartProduct> cpList = cartProdrepo.get();
		return ResponseEntity.ok(cpList);
		
		
	}
	
	@PostMapping("/add-to-cart/{pId}")
	public ResponseEntity<?> addProductToCart(@PathVariable int pId,@RequestBody User user, MessageDto dto){
		String username = user.getUsername();
		try{
		Customer customer = customerRepository.getCustomerByUsername(username);
		Product product = productRepository.findById(pId).get();
		
		CartProduct cartProduct = new CartProduct();
		
		Cart cart;
		Optional<Cart> cartopt = cartRepository.getCartByCustomer(customer);
		if(cartopt.isEmpty()) {
			cart = new Cart();
			cart.setCustomer(customer);
			cart.setCartStatus(StatusType.ACTIVE);
		}
		else {
			cart = cartopt.get();
		}
		
		cartProduct.setCart(cart);
		cartProduct.setProduct(product);
		cartProduct.setProductQuantity(1);
		cartProduct.setStatus(StatusType.ACTIVE);
		cartProduct.setAmountPayable(product.getPrice());
		
		
		double q = product.getQuantity();
		if(q>1) product.setQuantity(q-1);
		
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			ResponseEntity.badRequest().body(dto);
		}
		return ResponseEntity.ok("Product added to cart");
	}
	
	@PostMapping("/add-to-wishlist/{pId}")
	public ResponseEntity<?> addProductToWishlist(@PathVariable int pId,@RequestBody User user, MessageDto dto){
		String username = user.getUsername();
		try{
		Customer customer = customerRepository.getCustomerByUsername(username);
		Product product = productRepository.findById(pId).get();
		
		WishlistProduct wishlistProduct = new WishlistProduct();
		
		Wishlist wishlist;
		Optional<Wishlist> wlopt = wishlistRepository.getWishlistByCustomer(customer);
		if(wlopt.isEmpty()) {
			wishlist = new Wishlist();
			wishlist.setCustomer(customer);
		}
		else {
			wishlist = wlopt.get();
		}
		
		wishlistProduct.setWishlist(wishlist);
		wishlistProduct.setProduct(product);
		
		double q = product.getQuantity();
		if(q>1) product.setQuantity(q-1);
		
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			ResponseEntity.badRequest().body(dto);
		}
		return ResponseEntity.ok("Product added to wishlist");
	}
	
	
	@GetMapping("/my-wishlist")
	public ResponseEntity<?> getProductsFromWishlist(@RequestBody Customer customer,MessageDto dto){
			Optional<Wishlist> wishlist = customerService.getProductsFromWishlist(customer);
			if(!wishlist.isEmpty()) {
				return ResponseEntity.ok(wishlist);
			}
			else {
				dto.setMsg("Customer not registered");
				return ResponseEntity.badRequest().body(dto);
			}		
	}
	
	
//	customer order api 
//	check prod availability
	
	@PostMapping("/order")
	public ResponseEntity<?> customerOrder(@RequestBody User user,MessageDto dto,OrderInvoiceDto orderDto){
		
		Customer customer;
		Optional<Cart> cart;
		Optional<List<CartProduct>> cartProdrepo;
		try {
			customer = customerRepository.getCustomerByUser(user);
			cart = cartRepository.getCartByCustomer(customer);
			cartProdrepo = cartProductRepository.getCartProductsByCart(cart.get());
			cart.get().setCartQuantity(cartProdrepo.get().size());
			Optional<Order> order = customerService.customerOrder(customer,cart.get());
			
			if(!order.isEmpty()) {
				Order custOrder = order.get();
				
				orderDto.setAmount(custOrder.getOrderAmount() - custOrder.getOrderDiscount() + custOrder.getOrderFee());
				orderDto.setOrderId(custOrder.getId());
				orderDto.setMsg("Order Placed");
				return ResponseEntity.ok(orderDto);
			}
			else {
				dto.setMsg("Cart is empty!!");
				return ResponseEntity.badRequest().body(dto);
			}
		} catch (Exception e) {
			dto.setMsg(e.getMessage());
			ResponseEntity.badRequest().body(dto);
		}
		dto.setMsg("Not happening");
		return ResponseEntity.badRequest().body(dto);
	}
	
	@PostMapping("/order-now/{pId}")
	public ResponseEntity<?> customerOrderNow(@PathVariable("pId") int pId,@RequestBody User user,MessageDto dto,OrderInvoiceDto orderDto){
		
		Customer customer = customerRepository.getCustomerByUser(user);
		
		Optional<Product> product = productRepository.findById(pId);
		if(product.isEmpty()){
			dto.setMsg("Product not available");
			return ResponseEntity.badRequest().body(dto);
		}	
		if(product.get().isOutOfStock()) {
			dto.setMsg("Product not available");
			return ResponseEntity.badRequest().body(dto);
		}
		
//		warehouseManagerService.sendStockAlerts(product.get());
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		
		CartProduct cartProduct = new CartProduct();
		cartProduct.setCart(cart);
		cartProduct.setProduct(product.get());
		
		Order order = new Order();
		order.setCart(cart);
		
		OrderProduct op = new OrderProduct();
		op.setAmountPayable((product.get().getPrice()-product.get().getDiscount())*product.get().getQuantity());
		op.setDiscount(product.get().getDiscount()*product.get().getQuantity());
		op.setOrder(order);
		op.setProduct(product.get());
		
		
		order.setOrderAmount(op.getAmountPayable());
		order.setOrderDiscount(op.getDiscount());
		order.setStatus(OrderStatus.ORDERED);
		
		int fee = 100;
		if(order.getOrderAmount()>500) fee=0;
		
		order.setOrderFee(fee);
		
		return ResponseEntity.ok("Ordered successfully!");
	}
}
		
//		Optional<OrderProduct> orderProduct = customerService.customerOrderProduct(customer,prodId);
//		if(!orderProduct.isEmpty()) {
//			OrderProduct custOrder = orderProduct.get();
//			
//			orderDto.setAmount(custOrder.getOrderAmount() - custOrder.getOrderDiscount() + custOrder.getOrderFee());
//			orderDto.setOrderId(custOrder.getId());
//			orderDto.setMsg("Order Placed");
//			return ResponseEntity.ok(orderDto);
//		}
//		else 
//	}
//	
//}
