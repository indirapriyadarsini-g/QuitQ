package com.quitq.ECom.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.config.JwtUtil;
import com.quitq.ECom.dto.TokenDto;
import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Wishlist;
import com.quitq.ECom.repository.CartRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.WishlistRepository;
import com.quitq.ECom.service.MyUserDetailsService;



@CrossOrigin(origins={"http://localhost:4200"})
@RestController


public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired 
    private CartRepository cartRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtUtil jwtUtil;
   
    @PostMapping("/auth/token")
    public TokenDto createAuthenticationToken(@RequestBody User authenticationRequest,TokenDto dto) throws Exception {
 
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
 
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        System.out.println(jwt);
 dto.setToken(jwt);
        return dto;
    }
    @PostMapping("/auth/signup")
    public void signup(@RequestBody User userInfo) {
    	userInfo.setRole("ROLE_CUSTOMER");
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
    	Customer customer = new Customer();
    	customer.setUser(userInfo);
    	customerRepository.save(customer);
    	Cart cart = new Cart();
    	Wishlist wishlist = new Wishlist();
    	cart.setCustomer(customer);
    	wishlist.setCustomer(customer); 
    	cartRepository.save(cart);
    	wishlistRepository.save(wishlist);
    }
   
    
    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello, User!";
    }
    @GetMapping("/auth/login")
    public User login(Principal p) {
    	String username=p.getName();
    	User u=userRepository.getUserByUsername(username);
    	return u;
    }
 
 
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello, Admin!";
    }
   
 
    
  
  
}


