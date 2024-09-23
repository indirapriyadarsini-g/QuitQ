
package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.UserInfoRepository;
import com.quitq.ECom.repository.WishlistRepository;
import com.quitq.ECom.service.CustomerService;
//import com.quitq.ECom.service.WarehouseManagerService;

@RestController
@RequestMapping("customer/")
public class CustomerController {

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
			cust.getUserInfo().setRole(RoleType.CUSTOMER);
			customerService.register(cust);
			
			Cart cart = new Cart();
			cart.setCustomer(cust);
			
			Wishlist wishlist = new Wishlist();
			wishlist.setCustomer(cust);
			
			return ResponseEntity.ok(new String("Customer details registered"));
		} catch (Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	
	@GetMapping("/view-my-cart")
	public ResponseEntity<?> getProductsFromCart(Principal principal,MessageDto dto){
		Optional<List<CartProduct>> cartProdrepo;
		try {
			Optional<Cart> cart = cartRepository.getCartByUsername(principal.getName());
			cartProdrepo = cartProductRepository.getCartProductsByCart(cart.get());
		} catch (Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		if(cartProdrepo.isEmpty()) {
			dto.setMsg("No products inside cart");
			return ResponseEntity.badRequest().body(dto);
		}

		List<CartProduct> cpList = cartProdrepo.get();
		return ResponseEntity.ok(cpList);
		
		
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
		
			return ResponseEntity.ok(cartProduct);
			}
		else {
			dto.setMsg("Product not available");
			return ResponseEntity.badRequest().body(dto);
		}
		
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		
	}
	
	@DeleteMapping("/remove-from-cart")
	public ResponseEntity<?> removeProductFromCart(@PathVariable int cpId,Principal principal, MessageDto dto){
		return null;
		
	}
	
	
	@PostMapping("/add-to-wishlist/{pId}")
	public ResponseEntity<?> addProductToWishlist(@PathVariable int pId,@RequestBody UserInfo user, MessageDto dto){
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
	public ResponseEntity<?> getProductsFromWishlist(Principal principal,MessageDto dto){
			Optional<Wishlist> wishlist = customerService.getProductsFromWishlist(principal.getName());
			if(!wishlist.isEmpty()) {
				return ResponseEntity.ok(wishlist);
			}
			else {
				dto.setMsg("Customer not registered");
				return ResponseEntity.badRequest().body(dto);
			}		
	}
	
	
	@PutMapping("/add-count-in-cart")
	public ResponseEntity<?> addProductCountInCart(Principal principal,@RequestBody CartProduct cartProduct){
//		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		return customerService.incrementProductCountInCart(cartProduct);
	}
	
//	@PutMapping("/sub-count-in-cart")
//	public ResponseEntity<?> subProductCountInCart(Principal principal,@RequestBody CartProduct cartProduct){
//		return customerService.decrementProductCountInCart(cartProduct);
//	}
//	
//	@PutMapping("/add-count-in-order")
//	public ResponseEntity<?> addProductCountInOrder(Principal principal,@RequestBody CartProduct cartProduct){
//		return customerService.incrementProductCountInOrder(cartProduct);
//	}
//	
//	@PutMapping("/sub-count-in-order")
//	public ResponseEntity<?> subProductCountInOrder(Principal principal,@RequestBody CartProduct cartProduct){
//		return customerService.decrementProductCountInOrder(cartProduct);
//	}
	
	
	
	
//	customer order api 
//	check prod availability
	
	@PostMapping("/order")
	public ResponseEntity<?> customerOrder(Principal principal,MessageDto dto,OrderInvoiceDto orderDto){
		
		Customer customer;
		Optional<Cart> cart;
		Optional<List<CartProduct>> cartProdrepo;
		try {
			customer = customerRepository.getCustomerByUsername(principal.getName());
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
 