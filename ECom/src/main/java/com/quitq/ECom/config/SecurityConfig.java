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
            		   .requestMatchers("/auth/login").permitAll()
            		   .requestMatchers("/auth/signup").permitAll()  

            		   .requestMatchers("/error").permitAll()
            		   .requestMatchers("/customer/register-profile").permitAll()
            		   .requestMatchers("/customer/view-all-product").permitAll()
            		   .requestMatchers("/customer/view-my-profile").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/view-my-cart").hasRole("CUSTOMER")		
            		   .requestMatchers("/customer/view-my-wishlist").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/order-now/{pId}").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/order").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/add-to-cart/{pId}").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/add-to-wishlist/{pId}").hasRole("CUSTOMER")	
            		   .requestMatchers("/customer/remove-from-order/{cpId}").hasRole("CUSTOMER")
            		   .requestMatchers("/customer/add-count-in-cart/").hasRole("CUSTOMER")
            		   .requestMatchers("/customer/sub-count-in-cart/").hasRole("CUSTOMER")
            		   .requestMatchers("/customer/add-count-in-order/").hasRole("CUSTOMER")
            		   .requestMatchers("/customer/sub-count-in-order/").hasRole("CUSTOMER")
            		   .requestMatchers("/customer/cart/refresh").hasRole("CUSTOMER")
            		   
            		   
                       .requestMatchers("/vendor/get").permitAll()
                       .requestMatchers("/vendor/getAll").hasRole("EXECUTIVE")
                       .requestMatchers("/vendor/getAllCategory").permitAll()

                       .requestMatchers("/vendor/delete").hasRole("VENDOR")
                       .requestMatchers("/vendor/update").permitAll()
                       .requestMatchers("/product/add").permitAll()
                       .requestMatchers("/product/delete/{id}").permitAll()
                       .requestMatchers("/product/delete/v2/{id}").permitAll()

                       .requestMatchers("/product/update/{id}").hasRole("VENDOR")
                       .requestMatchers("/product/vendor").permitAll()
                       .requestMatchers("/product/vendor/categorySold").hasRole("VENDOR")
                       .requestMatchers("/product/vendor/productName/{name}").hasRole("VENDOR")
                       .requestMatchers("/product/vendor/addAll/upload").hasRole("VENDOR")
                       .requestMatchers("/product/vendor/orderBy/{order}/asc").hasRole("VENDOR")
                       .requestMatchers("/product/vendor/orderBy/{order}/desc").hasRole("VENDOR")
                       .requestMatchers("/product/vendor/status/{name}").permitAll()
                       .requestMatchers("/product/vendor/changeStatus/{id}").permitAll()
                       .requestMatchers("/product/vendor/product/outOfStock").permitAll()
                       .requestMatchers("/image/add/{id}/{status}").permitAll()
                       .requestMatchers("/image/getAll/{id}").permitAll()
                       .requestMatchers("/image/getSpecificImage/{id}").permitAll()
                       .requestMatchers("/image/deleteAllImage/{id}").hasRole("VENDOR")
                       .requestMatchers("/image/deleteSpecificImage/{id}").hasRole("VENDOR")
                       .requestMatchers("/image/update/{id}").permitAll()
                       .requestMatchers("/image/coverImage/{id}").hasRole("VENDOR")
                       .requestMatchers("/address/vendor/add").hasRole("VENDOR")
                       .requestMatchers("/address/vendor/all").hasRole("VENDOR")
					   .requestMatchers("/address/vendor/update/{aid}").hasRole("VENDOR")

					   .requestMatchers("/address/vendor/activeAddress").hasRole("VENDOR")
					   .requestMatchers("/address/vendor/chanegStatus/{aid}/{status}").hasRole("VENDOR")
					   .requestMatchers("/address/vendor/get/{aid}").hasRole("VENDOR")
					   .requestMatchers("/orderproduct/vendor/getAll").permitAll()
					   .requestMatchers("/orderproduct/vendor/getAllUnordered").permitAll()
					   .requestMatchers("/orderproduct/vendor/product/details").permitAll()
					   .requestMatchers("/orderproduct/vendor/product/details/{status}").permitAll()
					   .requestMatchers("/orderproduct/vendor/orderReceive/{month}").hasRole("VENDOR")
					   .requestMatchers("/orderproduct/vendor/orderReceiveDate/{date}").hasRole("VENDOR")
					   .requestMatchers("/orderproduct/vendor/noOfProductOrdered/{month}").hasRole("VENDOR")
					   .requestMatchers("/orderproduct/vendor/productOrdered/{date}").hasRole("VENDOR")
					   .requestMatchers("/orderproduct/vendor/topSellingProduct/{month}").hasRole("VENDOR")
               .requestMatchers("/orderproduct/vendor/topOrdered/{month}").hasRole("VENDOR")

               .requestMatchers("/orderproduct/vendor/returnOrder").permitAll()
               .requestMatchers("/orderproduct/vendor/exchangeOrder").permitAll()

               .requestMatchers("/orderproduct/vendor/productStats/{month}").hasRole("VENDOR")
               .requestMatchers("/orderproduct/vendor/returnOrder").hasRole("VENDOR")
               .requestMatchers("/orderproduct/vendor/exchangeOrder").hasRole("VENDOR")

               .requestMatchers("/warehouse-manager/getwmgr/{warehouseId}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse-manager/createmgr").permitAll()
               .requestMatchers("/warehouse-manager/updatemgr/{id}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse-manager/deletemgr/{id}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/get/{city}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/addwh").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/updatewh/{id}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/deletewh/{id}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/addp2w/{warehouseId}/products/{productId}").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/getpnotinw/{warehouseId}/products/not-in").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/getstockbyname/{productName}/stock-quantity").hasRole("WAREHOUSEMANAGER")
               .requestMatchers("/warehouse/zero-stock").hasRole("WAREHOUSEMANAGER")


               

               .requestMatchers("/product/vendorName/{name}").permitAll()

.requestMatchers("/product/category/{name}").permitAll()
.requestMatchers("/review/getAll/{pid}").permitAll()
            		   .requestMatchers("/vendor/add").permitAll()
            		   .requestMatchers("/auth/login").permitAll()

                       .requestMatchers("/auth/token").permitAll()
                       .requestMatchers("/auth/signup").permitAll()
                       .requestMatchers("/admin/hello").hasRole("ADMIN")
                       .requestMatchers("/user/hello").hasAnyRole("USER","ADMIN")
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
