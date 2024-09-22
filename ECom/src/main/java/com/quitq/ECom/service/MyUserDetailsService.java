package com.quitq.ECom.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.repository.UserInfoRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserInfoRepository userRepository; 
	

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.getUserInfoByUsername(username);
                
 
        return new org.springframework.security.core.userdetails.User(userInfo.getUsername(), userInfo.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userInfo.getRole().toString())));
    }
	

}

