package com.quitq.ECom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.config.JwtUtil;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.model.User;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.service.CustomerService;
import com.quitq.ECom.service.MyUserDetailsService;
import com.quitq.ECom.service.UserService;


@RestController
public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/auth/signup")
    public void signup(@RequestBody User userInfo) {
    	userInfo.setRole(RoleType.USER);
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> customerLogin(@RequestBody User user, MessageDto dto){
		try {
			userService.login(user);
			return ResponseEntity.ok(new String("User logged in"));
		} catch (Exception e) {
			dto.setMsg("Invalid credentials");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@PostMapping("/auth/token")
    public String createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
 
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
 
        return jwt;
    }
}
