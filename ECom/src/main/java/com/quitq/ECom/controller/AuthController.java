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
import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.repository.UserInfoRepository;
import com.quitq.ECom.service.MyUserDetailsService;



@RestController
@CrossOrigin(origins={"http://localhost:4200"})

public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
	private UserInfoRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/auth/token")
    public TokenDto createAuthenticationToken(@RequestBody UserInfo authenticationRequest,TokenDto dto) throws Exception {
 
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
        dto.setToken(jwt);
        
        
        return dto;
    }
    @PostMapping("/auth/signup")
    public void signup(@RequestBody UserInfo userInfo) {
    	userInfo.setRole("ROLE_CUSTOMER");
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
    }
   
    
    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello, User!";
    }
 
 
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello, Admin!";
    }
    @GetMapping("/auth/login")
    public UserInfo login(Principal p) {
    	String username=p.getName();
    	UserInfo u=userRepository.getUserInfoByUsername(username);
    	return u;
    }
 
    
  
  
}


