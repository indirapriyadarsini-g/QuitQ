
package com.quitq.ECom.controller;

import java.security.Principal;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.dto.CartProductDto;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.dto.OrderInvoiceDto;
import com.quitq.ECom.dto.WishlistProductDto;
import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.enums.StatusType;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.OrderRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.UserInfoRepository;
import com.quitq.ECom.repository.WishlistRepository;
import com.quitq.ECom.service.CustomerService;
//import com.quitq.ECom.service.WarehouseManagerService;

@RestController
@RequestMapping("customer/")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserInfoRepository userRepository;
	
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
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	
//	@Autowired
//	private WarehouseManagerService warehouseManagerService;
	
	@PostMapping("/register-profile")
	public ResponseEntity<?> customerRegister(@RequestBody Customer customer,Principal principal, MessageDto dto){
		try {
			UserInfo userInfo = userRepository.getUserInfoByUsername(principal.getName());
			Customer cust = new Customer();
			cust.setContact(customer.getContact());
			cust.setName(customer.getName());
			cust.setUserInfo(userInfo);
			cust.getUserInfo().setRole("ROLE_CUSTOMER");
			Customer c = customerService.register(cust);
			
			Cart cart = new Cart();
			cart.setCustomer(cust);
			
			Wishlist wishlist = new Wishlist();
			wishlist.setCustomer(cust);
			logger.info("Profile Registered");
			return ResponseEntity.ok(c);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/view-my-profile")
	public ResponseEntity<?> viewCustomerProfile(Principal principal,MessageDto dto){
		Customer customer = customerService.getProfileDetails(principal.getName());
		if(customer!=null) return ResponseEntity.ok(customer);
		else return ResponseEntity.badRequest().body("Customer not registered");
	}
	
	
	@GetMapping("/view-my-cart")
	public ResponseEntity<?> getProductsFromCart(Principal principal,MessageDto dto){
		
			List<CartProductDto> cartProdDtoList = customerService.getCartProductDtoByUsername(principal.getName());
		
//			Optional<List<CartProduct>> cartProdList = customerService.getCartProductByUsername(principal.getName());
			if(cartProdDtoList!=null) return ResponseEntity.ok(cartProdDtoList);
		else {
			dto.setMsg("No products inside the cart");
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@PostMapping("/add-to-cart/{pId}")
	public ResponseEntity<?> addProductToCart(@PathVariable int pId,Principal principal, MessageDto dto){
		String username = principal.getName();
		try{
		Customer customer = customerRepository.getCustomerByUsername(username);
		Product product = productRepository.findById(pId).get();
		if(product.getQuantity()>1) {
			CartProduct cartProduct = new CartProduct();
		
			Cart cart;
			Optional<Cart> cartopt = cartRepository.getCartByCustomer(customer);
			if(cartopt.isEmpty()) {
				cart = new Cart();
				cart.setCustomer(customer);
			}
			else {
				cart = cartopt.get();
			}
		
			cartProduct.setCart(cart);
			cartProduct.setProduct(product);
			cartProduct.setProductQuantity(1);
			cartProduct.setStatus(StatusType.ACTIVE);
			cartProduct.setAmountPayable(product.getPrice());
		
			cartProductRepository.save(cartProduct);
		
			product.setQuantity(product.getQuantity()-1);
		
			logger.info("Product added to cart");
			
			return ResponseEntity.ok(cartProduct);
			}
		else {
			logger.warn("Product not available");
			dto.setMsg("Product not available");
			return ResponseEntity.badRequest().body(dto);
		}
		
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}		
	}
	
	@DeleteMapping("/remove-from-cart/{cpId}")
	public ResponseEntity<?> removeProductFromCart(@PathVariable int cpId,Principal principal, MessageDto dto){
		try {
			Optional<Cart> cart = cartRepository.getCartByUsername(principal.getName());
			int n = cartProductRepository.deleteCartProductsByCart(cart.get());
			if(n<1)	throw new Exception("No cartProduct deleted");
			logger.info("Deleted");
			return ResponseEntity.ok(new String(""+n+" products deleted"));
		}catch(Exception e) {
			logger.warn(e.getMessage());
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}		
	}
	
	@DeleteMapping("/remove-from-order/{opId}")
	public ResponseEntity<?> removeProductFromOrder(@PathVariable int opId,Principal principal, MessageDto dto){
		try {
			Optional<Order> order = orderRepository.getOrderByUsername(principal.getName());
			int n = orderProductRepository.deleteOrderProductsByOrder(order.get());
			if(n<1)	throw new Exception("No cartProduct deleted");
			return ResponseEntity.ok(new String(""+n+" products deleted"));
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}		
	}
	
	
	@PostMapping("/add-to-wishlist/{pId}")
	public ResponseEntity<?> addProductToWishlist(@PathVariable int pId,Principal principal, MessageDto dto){
		try{
		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		Product product = productRepository.findById(pId).get();
		
		WishlistProduct wishlistProduct = new WishlistProduct();
		
		Wishlist wishlist = wishlistRepository.getWishlistByCustomer(customer);
		
		
		wishlistProduct.setWishlist(wishlist);
		wishlistProduct.setProduct(product);
		
		double q = product.getQuantity();
		if(q>1) product.setQuantity(q-1);
		
		return ResponseEntity.ok(wishlistProduct);
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	
	@GetMapping("/view-my-wishlist")
	public ResponseEntity<?> getProductsFromWishlist(Principal principal,MessageDto dto){
		List<WishlistProductDto> wlProdDtoList = customerService.getWishlistProductDtoByUsername(principal.getName());
		
//		Optional<List<WishlistProduct>> wlProdList = customerService.getWishlistProductByUsername(principal.getName());

		if(wlProdDtoList!=null) return ResponseEntity.ok(wlProdDtoList);
	else {
		dto.setMsg("No products inside the wishlist");
		return ResponseEntity.badRequest().body(dto);
	}
		
	}
	
	
	@PutMapping("/add-count-in-cart")
	public ResponseEntity<?> addProductCountInCart(Principal principal,@RequestBody CartProduct cartProduct){
//		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		return customerService.incrementProductCountInCart(cartProduct);
	}
	
	@PutMapping("/sub-count-in-cart")
	public ResponseEntity<?> subProductCountInCart(Principal principal,@RequestBody CartProduct cartProduct){
		return customerService.decrementProductCountInCart(cartProduct);
	}
	
	@PutMapping("/add-count-in-order")
	public ResponseEntity<?> addProductCountInOrder(Principal principal,@RequestBody OrderProduct orderProduct){
		return customerService.incrementProductCountInOrder(orderProduct);
	}
	
	@PutMapping("/sub-count-in-order")
	public ResponseEntity<?> subProductCountInOrder(Principal principal,@RequestBody OrderProduct orderProduct){
		return customerService.decrementProductCountInOrder(orderProduct);
	}
	
	
	
	
//	customer order api 
//	check prod availability
	
	@PostMapping("/order")
	public ResponseEntity<?> customerOrder(Principal principal,MessageDto dto,OrderInvoiceDto orderDto){
		
		Optional<Cart> cart;
		Optional<List<CartProduct>> cartProdrepo;
		try {
			cart = cartRepository.getCartByUsername(principal.getName());
			cartProdrepo = cartProductRepository.getCartProductsByCart(cart.get());
			cart.get().setCartQuantity(cartProdrepo.get().size());
			Optional<Order> order = customerService.customerOrder(cart.get());
			
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
		dto.setMsg("Not updating");
		return ResponseEntity.badRequest().body(dto);
	}
	
	@PostMapping("/order-now/{pId}")
	public ResponseEntity<?> customerOrderNow(@PathVariable int pId,Principal principal,MessageDto dto,OrderInvoiceDto orderDto){
		
		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		
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
		
		return ResponseEntity.ok(order);
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
 