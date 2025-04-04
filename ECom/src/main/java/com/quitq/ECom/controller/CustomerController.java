
package com.quitq.ECom.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.dto.CartProductDto;
import com.quitq.ECom.dto.ExchangeDto;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.dto.OrderInvoiceDto;
import com.quitq.ECom.dto.OrderProductWImageDto;
import com.quitq.ECom.dto.OrderSummaryDto;
import com.quitq.ECom.dto.ProductWImageDto;
import com.quitq.ECom.dto.ReturnDto;
import com.quitq.ECom.dto.WishlistProductDto;
import com.quitq.ECom.enums.ExchangeStatus;
import com.quitq.ECom.enums.OrderStatus;
import com.quitq.ECom.enums.RefundStatus;
import com.quitq.ECom.enums.ReturnStatus;
import com.quitq.ECom.enums.StatusType;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Exchange;
import com.quitq.ECom.model.ExchangeReason;
import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Return;
import com.quitq.ECom.model.ReturnReason;
import com.quitq.ECom.model.Review;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.model.WishlistProduct;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.ExchangeReasonRepository;
import com.quitq.ECom.repository.ExchangeRepository;
import com.quitq.ECom.repository.ImageRepository;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.OrderRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.ReturnReasonRepository;
import com.quitq.ECom.repository.ReturnRepository;
import com.quitq.ECom.repository.ReviewRepository;
import com.quitq.ECom.repository.WishlistProductRepository;
import com.quitq.ECom.repository.WishlistRepository;
import com.quitq.ECom.service.CustomerService;
//import com.quitq.ECom.service.WarehouseManagerService;

@RestController
@CrossOrigin(origins={"http://localhost:4200"})
@RequestMapping("customer/")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private WishlistProductRepository wishlistProductRepository;
	
	
	
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
	private ImageRepository imageRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
@Autowired
private ReviewRepository reviewRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ReturnRepository returnRepository;
	
	@Autowired
	private ReturnReasonRepository returnReasonRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private ExchangeReasonRepository exchangeReasonRepository;
	
//	@Autowired
//	private WarehouseManagerService warehouseManagerService;
	
	@PostMapping("/register-profile")
	public ResponseEntity<?> customerRegister(@RequestBody Customer customer,Principal principal, MessageDto dto){
		try {
			
			Customer cust = customerRepository.getCustomerByUsername(principal.getName());
			cust.setContact(customer.getContact());
			cust.setName(customer.getName());
			cust.getUser().setRole("ROLE_CUSTOMER");
			Customer c = customerService.register(cust);
			
			
			logger.info("Profile Registered");
			return ResponseEntity.ok(c);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/view-all-product")
	public ResponseEntity<?> getAllProduct(MessageDto dto){
		List<ProductWImageDto> prodList = customerService.getAllProduct();
		logger.info("Products listed");
			return ResponseEntity.ok(prodList);
	}
	
	
	
	@GetMapping("/view-my-profile")
	public ResponseEntity<?> viewCustomerProfile(Principal principal,MessageDto dto){
		Customer customer = customerService.getProfileDetails(principal.getName());
		if(customer!=null) return ResponseEntity.ok(customer);
		logger.info("Profile displayed");
		 return ResponseEntity.badRequest().body("Customer not registered");
	}
	
	
	@GetMapping("/view-my-cart")
	public ResponseEntity<?> getProductsFromCart(Principal principal,MessageDto dto){
		
			List<CartProductDto> cartProdDtoList = customerService.getCartProductDtoByUsername(principal.getName());
			
			
			if(cartProdDtoList!=null) return ResponseEntity.ok(cartProdDtoList);
		else {
			dto.setMsg("No products inside the cart");
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<?> addProductToCart(@RequestBody Product product,Principal principal, MessageDto dto){
		String username = principal.getName();
		try{
		Customer customer = customerRepository.getCustomerByUsername(username);
//		Product product = productRepository.findById(pId).get();
		if(product.getQuantity()>1) {
			CartProduct cartProduct = new CartProduct();
		
			Cart cart = cartRepository.getCartByCustomer(customer);
			
			Optional<CartProduct> cpExisting = cartProductRepository.getCartProductByCartAndProduct(cart,product);
			
			if(cpExisting.isPresent()) {
				dto.setMsg("Already exists in cart");
				return ResponseEntity.badRequest().body(dto);
			}
			
			cartProduct.setCart(cart);
			cartProduct.setProduct(product);
			cartProduct.setProductQuantity(1);
			cartProduct.setStatus(StatusType.ACTIVE);
			cartProduct.setAmountPayable(product.getPrice());
		
			cartProductRepository.save(cartProduct);
		
			cart.setCartQuantity(cart.getCartQuantity()+1);
			cart.setCartTotal(cart.getCartTotal()+cartProduct.getAmountPayable());
			cart.getProductList().add(cartProduct.getProduct());
			cartRepository.save(cart);
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
	
	@DeleteMapping("/remove-from-cart/{cpid}")
	public ResponseEntity<?> removeProductFromCart(@PathVariable int cpid,Principal principal, MessageDto dto){
		try {
			Cart cart = cartRepository.getCartByUsername(principal.getName());
			CartProduct cp = cartProductRepository.findById(cpid).get();
			Cart toverify = cp.getCart();
			if(cart == toverify) {
			cartProductRepository.delete(cp);
			cart.setCartQuantity(cart.getCartQuantity() - cp.getProductQuantity());
			cart.setCartTotal(cart.getCartTotal() - cp.getAmountPayable());
			cart.getProductList().remove(cp.getProduct());
			cartRepository.save(cart);
			}
			logger.info("Deleted");
			dto.setMsg("Deleted");
			return ResponseEntity.ok(dto);
		}catch(Exception e) {
			logger.warn(e.getMessage());
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}		
	}
	
	@DeleteMapping("/remove-from-order/{opId}")
	public ResponseEntity<?> removeProductFromOrder(@PathVariable int opId, MessageDto dto){
		try {
//			Optional<OrderProduct> orderProduct = orderProductRepository.getOrderByUsername(principal.getName());
			int n = orderProductRepository.deleteOrderProductsByOrderId(opId);
			if(n<1)	throw new Exception("No cartProduct deleted");
			return ResponseEntity.ok(new String(""+n+" products deleted"));
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}		
	}
	
	
	@PostMapping("/add-to-wishlist")
	public ResponseEntity<?> addProductToWishlist(@RequestBody Product product,Principal principal, MessageDto dto){
		try{
		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		
		
		WishlistProduct wishlistProduct = new WishlistProduct();
		
		Wishlist wishlist = wishlistRepository.getWishlistByCustomer(customer);
		
		
		wishlistProduct.setWishlist(wishlist);
		wishlistProduct.setProduct(product);
		
		wishlistProductRepository.save(wishlistProduct);
		
		
		
		return ResponseEntity.ok(wishlistProduct);
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@DeleteMapping("/remove-from-wishlist/{wpId}")
	public ResponseEntity<?> removeFromWishlist(@PathVariable int wpId, MessageDto dto){
		try {
			 wishlistProductRepository.deleteById(wpId);
			 dto.setMsg("Deleted");
			return ResponseEntity.ok(dto);
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
	
	@GetMapping("/get-pwi-by-pid/{pId}")
	public ResponseEntity<?> getPwiByProdId(@PathVariable int pId){
		ProductWImageDto pwi = new ProductWImageDto();
		Product p = productRepository.findById(pId).get();
		List<Image> imList = imageRepository.getImageByProduct(p);
		pwi.setImageList(imList);
		pwi.setProduct(p);
		return ResponseEntity.ok(pwi);
	}
	
	
	
//	customer order api 
//	check prod availability
	
	@PostMapping("/order")
	public ResponseEntity<?> customerOrder(@RequestBody String username,Principal principal, MessageDto dto,OrderInvoiceDto orderDto){
		System.out.println("Inside ordering");
		
		Optional<List<CartProduct>> cartProdrepo;
		try {
			System.out.println("Inside try");
			Cart cart = cartRepository.getCartByUsername(username);
			System.out.println("cart received");
			cartProdrepo = cartProductRepository.getCartProductsByCart(cart);
			System.out.println("cartprods received");
			cart.setCartQuantity(cartProdrepo.get().size());
			cartRepository.save(cart);
			OrderSummaryDto osdto = customerService.customerOrder(cart);
			System.out.println(osdto.toString());
			System.out.println("order done");
			return ResponseEntity.ok(osdto);
		} catch (Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@PostMapping("/order-now/{pId}")
	public ResponseEntity<?> customerOrderNow(@PathVariable int pId,Principal principal,MessageDto dto,OrderInvoiceDto orderDto){
		
		Customer customer = customerRepository.getCustomerByUsername(principal.getName());
		
		Product product = productRepository.findById(pId).get();
		
		if(product.isOutOfStock()) {
			dto.setMsg("Product not available");
			return ResponseEntity.badRequest().body(dto);
		}
		
		
//		warehouseManagerService.sendStockAlerts(product.get());
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		
		CartProduct cartProduct = new CartProduct();
		cartProduct.setCart(cart);
		cartProduct.setProduct(product);
		
		Order order = new Order();
		order.setCart(cart);
		
		OrderProduct op = new OrderProduct();
		op.setAmountPayable((product.getPrice()-product.getDiscount())*product.getQuantity());
		op.setDiscount(product.getDiscount()*product.getQuantity());
		op.setOrder(order);
		op.setProduct(product);
		
		
		order.setOrderAmount(op.getAmountPayable());
		order.setOrderDiscount(op.getDiscount());
		order.setStatus(OrderStatus.ORDERED);
		
		int fee = 100;
		if(order.getOrderAmount()>500) fee=0;
		
		order.setOrderFee(fee);
		
		orderRepository.save(order);
		
		orderDto.setOrderId(order.getId());
		orderDto.setAmount(order.getOrderAmount()+order.getOrderFee());
		
		return ResponseEntity.ok(orderDto);
	}
	
	@GetMapping("/image-of-prodId/{pId}")
	public ResponseEntity<?> imageOfProduct(@PathVariable int pId,MessageDto dto){
		try {
			List<Image> imList = imageRepository.getImageByProductId(pId);
			return ResponseEntity.ok(imList);
		} catch (Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/view-my-orders")
	public ResponseEntity<?> viewOrderList(Principal principal, MessageDto dto){
		List<OrderProduct> opList = customerService.getOrderProductList(principal.getName());
		System.out.println(opList.get(0).toString());
		List<OrderProductWImageDto> opwiList = new ArrayList<>();
		
		for(OrderProduct op:opList) {
			OrderProductWImageDto opwi = new OrderProductWImageDto();
			opwi.setOrderProduct(op);
			
			List<Image> imList = imageRepository.getImageByProduct(op.getProduct());
			opwi.setImageList(imList);
			
			opwiList.add(opwi);
		}
		
		return ResponseEntity.ok(opwiList);
		
	}
	
	
	
	
	@GetMapping("/view-order-details/{opId}")
	public ResponseEntity<?> viewOrderDetails(@PathVariable int opId,MessageDto dto){
		OrderProduct orderProduct = orderProductRepository.findById(opId).get();
		OrderProductWImageDto opwi = new OrderProductWImageDto();
		List<Image> imList = imageRepository.getImageByProduct(orderProduct.getProduct());
		opwi.setImageList(imList);
		opwi.setOrderProduct(orderProduct);
		return ResponseEntity.ok(opwi);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchProduct(
			@RequestParam(defaultValue="none",required=false)String category,
			@RequestParam(defaultValue="0",required = false) int minDiscount,
			@RequestParam(defaultValue = "none", required = false) String prodName,
			@RequestParam(defaultValue = "no", required = false) String includeOutOfStock			
			){
		List<Product> prodList = customerService.searchProdByParam(category,minDiscount,prodName,includeOutOfStock);
		
		List<ProductWImageDto> pwiList = new ArrayList<>();
		for(Product p: prodList) {
			List<Image> imList = imageRepository.getImageByProduct(p);
			if(imList==null) imList = new ArrayList<>();
			ProductWImageDto pwi = new ProductWImageDto();
			pwi.setImageList(imList);
			pwi.setProduct(p);
			pwiList.add(pwi);
		}
		
		
		return ResponseEntity.ok(pwiList);
	}
	
//	@GetMapping("/search")
//	public ResponseEntity<?> searchProduct(@RequestBody SearchDto search, MessageDto dto){
//		
//		List<Product> prodList = customerService.searchProdByParam(
//				search.getCategory(),
//				search.getMinDiscount(),
//				search.getProdName(),
//				search.getIncludeOutOfStock());
//		return ResponseEntity.ok(prodList);
//	}
	
	
	
	
	@PostMapping("/add-address")
	public ResponseEntity<?> addAddress(@RequestBody Address address, Principal principal, MessageDto dto){
		try{
			customerService.addAddressByUsername(address,principal.getName());
			dto.setMsg("Address updated");
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		
	}
	
	@GetMapping("/view-address")
	public ResponseEntity<?> getAddress(Principal principal, MessageDto dto){
		try{
			List<Address> addressList = customerService.getAddressByUsername(principal.getName());
			return ResponseEntity.ok(addressList);
		}
		catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@PostMapping("/add-review/{opId}")
	public ResponseEntity<?> addReview(@RequestBody Review review, @PathVariable int opId ,Principal principal, MessageDto dto){
		try {
			Product p = orderProductRepository.getProductByOrderProductId(opId);
			OrderProduct op = orderProductRepository.findById(opId).get();
			customerService.addReview(p,op,review,principal.getName());
			dto.setMsg("Review Added");
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/view-product-reviews/{pId}")
	public ResponseEntity<?> viewProductReviews(@PathVariable int pId,MessageDto dto){
		try {
			Product product = productRepository.findById(pId).get();
			List<Review> reviewList = customerService.getProductReviews(product);
			return ResponseEntity.ok(reviewList);
		}
		catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/cart-info")
	public ResponseEntity<?> getCartInfo(Principal principal, MessageDto dto){
		try {
			Cart cart = cartRepository.getCartByUsername(principal.getName());
			return ResponseEntity.ok(cart);
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@GetMapping("/view-order-summary/{oId}")
	public ResponseEntity<?> getOrderSummary(Principal principal, @PathVariable int oId,MessageDto dto){
		try {
			Order order = orderRepository.findById(oId).get();
			List<OrderProduct> opList = orderProductRepository.getOrderProductsByOrder(order);
			
			List<OrderProductWImageDto> opwiList = new ArrayList<>();
			
			for(OrderProduct op: opList) {
				OrderProductWImageDto opwi = new OrderProductWImageDto();
				List<Image> imList = imageRepository.getImageByProduct(op.getProduct());
				opwi.setImageList(imList);
				opwi.setOrderProduct(op);
				
				opwiList.add(opwi);
			}
			
			OrderSummaryDto summarydto = new OrderSummaryDto(order,opwiList);
			return ResponseEntity.ok(summarydto);
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@PostMapping("/request-return")
	public ResponseEntity<?> requestReturn(Principal principal,@RequestBody ReturnDto rdto,MessageDto dto){
		try {
			Return r = new Return();
			ReturnReason rr = new ReturnReason();
			
			rr.setReason(rdto.getReturnReason());
			returnReasonRepository.save(rr);
			
			OrderProduct op = orderProductRepository.findById(rdto.getOpId()).get();
			
			r.setOrderProduct(op);
			r.setReturnAmount(rdto.getReturnAmount());
			r.setReturnQuantity(rdto.getReturnQuantity());
			r.setReturnReason(rr);			
			r.setRefundStatus(RefundStatus.NOT_REFUNDED);
			r.setReturnInitiatedDate(LocalDateTime.now());
			r.setReturnStatus(ReturnStatus.ReturnInitiated);
			
			return ResponseEntity.ok(returnRepository.save(r));
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@PostMapping("/request-exchange")
	public ResponseEntity<?> requestExchange(Principal principal,@RequestBody ExchangeDto edto,MessageDto dto){
		try {
			Exchange e = new Exchange();
			ExchangeReason er = new ExchangeReason();
			
			er.setExchangeReason(edto.getExchangeReason());
			exchangeReasonRepository.save(er);
			
			OrderProduct op = orderProductRepository.findById(edto.getOpId()).get();
			
			e.setOrderProduct(op);
			e.setExchangeQuantity(edto.getExchangeQuantity());
			e.setExchangeReason(er);			
			e.setExchangeStatus(ExchangeStatus.INITIATED);
			e.setExchangeInitiatedDate(LocalDateTime.now());
			
			
			return ResponseEntity.ok(exchangeRepository.save(e));
		}catch(Exception e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/my-reviews")
	public ResponseEntity<?> getMyReviews(Principal principal){
		List<Review> rList = reviewRepository.getReviewByUsername(principal.getName());
		return ResponseEntity.ok(rList);
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
 