package com.quitq.ECom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.service.MyUserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(auth -> auth
            		   .requestMatchers("/auth/token").permitAll() 
            		   .requestMatchers("/error").permitAll()
            		   .requestMatchers("/auth/signup").permitAll()  
            		   .requestMatchers("/customer/register-profile").permitAll()
            		   .requestMatchers("/customer/view-my-profile").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("/customer/view-my-cart").hasRole(RoleType.CUSTOMER.toString())		
            		   .requestMatchers("/customer/my-wishlist").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("customer/order-now/{pId}").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("customer/order").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("customer/add-to-cart/{pId}").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("customer/add-to-wishlist/{pId}").hasRole(RoleType.CUSTOMER.toString())	
            		   .requestMatchers("/auth/login").permitAll()
                       .requestMatchers("/vendor/get/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/vendor/getAll").hasRole(RoleType.EXECUTIVE.toString())
                       .requestMatchers("/vendor/delete").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/vendor/update").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/add").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/delete/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/update/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/categorySold").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/productName/{name}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/addAll/upload").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/orderBy/{order}/asc").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/orderBy/{order}/desc").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/status/{name}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/changeStatus/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/product/vendor/product/outOfStock").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/add/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/getAll/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/getSpecificImage/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/deleteAllImage/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/deleteSpecificImage/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/update/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/image/coverImage/{id}").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/address/vendor/add").hasRole(RoleType.VENDOR.toString())
                       .requestMatchers("/address/vendor/all").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/address/vendor/update/{aid}").hasRole(RoleType.VENDOR.toString())

					   .requestMatchers("/address/vendor/activeAddress").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/address/vendor/chanegStatus/{aid}/{status}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/address/vendor/get/{aid}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/getAll").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/getAllUnordered").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/product/details").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/product/details/{status}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/orderReceive/{month}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/orderReceiveDate/{date}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/noOfProductOrdered/{month}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/productOrdered/{date}").hasRole(RoleType.VENDOR.toString())
					   .requestMatchers("/orderproduct/vendor/topSellingProduct/{month}").hasRole(RoleType.VENDOR.toString())
               .requestMatchers("/orderproduct/vendor/topOrdered/{month}").hasRole(RoleType.VENDOR.toString())
               .requestMatchers("/orderproduct/vendor/productStats/{month}").hasRole(RoleType.VENDOR.toString())
               .requestMatchers("/orderproduct/vendor/returnOrder").hasRole(RoleType.VENDOR.toString())
               .requestMatchers("/orderproduct/vendor/exchangeOrder").hasRole(RoleType.VENDOR.toString())
               .requestMatchers("/product/vendorName/{name}").permitAll()

.requestMatchers("/product/category/{name}").permitAll()
.requestMatchers("/review/getAll/{pid}").permitAll()
            		   .requestMatchers("/vendor/add").permitAll()
                       .requestMatchers("/auth/token").permitAll()
                       .requestMatchers("/auth/signup").permitAll()
                       .requestMatchers("/admin/hello").hasRole(RoleType.ADMIN.toString())
                       .requestMatchers("/user/hello").hasAnyRole(RoleType.USER.toString(),RoleType.ADMIN.toString())
                       .anyRequest().permitAll()
               )
               .sessionManagement(session -> session
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               );

       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
   }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }
	
	@Bean 
	PasswordEncoder getEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
    DaoAuthenticationProvider authenticationProvider(MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(userDetailsService);
       authProvider.setPasswordEncoder(getEncoder());
       return authProvider;
   }
	
}
