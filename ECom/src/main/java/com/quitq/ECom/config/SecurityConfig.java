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
            		   .requestMatchers("/auth/login").permitAll()
                       .requestMatchers("/vendor/get/{id}").hasRole("VENDOR")
.requestMatchers("/vendor/getAll").hasRole("EXECUTIVE")
.requestMatchers("/product/add").hasRole("VENDOR")
.requestMatchers("/product/delete/{id}").hasRole("VENDOR")
.requestMatchers("/product/update/{id}").hasRole("VENDOR")
.requestMatchers("/product/vendor").hasRole("VENDOR")
.requestMatchers("/product/vendor/status/{name}").hasRole("VENDOR")
.requestMatchers("/product/vendor/changeStatus/{id}").hasRole("VENDOR")
.requestMatchers("/product/vendor/product/outOfStock").hasRole("VENDOR")
.requestMatchers("/image/add/{id}").hasRole("VENDOR")
.requestMatchers("/image/getAll/{id}").hasRole("VENDOR")
.requestMatchers("/image/getSpecificImage/{id}").hasRole("VENDOR")
.requestMatchers("/image/deleteAllImage/{id}").hasRole("VENDOR")
.requestMatchers("/image/deleteSpecificImage/{id}").hasRole("VENDOR")

.requestMatchers("/image/update/{id}").hasRole("VENDOR")
.requestMatchers("/image/coverImage/{id}").hasRole("VENDOR")
.requestMatchers("/address/vendor/add").hasRole("VENDOR")
.requestMatchers("/address/vendor/all").hasRole("VENDOR")



.requestMatchers("/product/vendorName/{name}").permitAll()

.requestMatchers("/product/category/{name}").permitAll()



            		   .requestMatchers("/vendor/add").permitAll()
                       .requestMatchers("/auth/token").permitAll()
                       .requestMatchers("/auth/signup").permitAll()
                       .requestMatchers("/admin/hello").hasRole("ADMIN")
                       .requestMatchers("/user/hello").hasAnyRole("USER", "ADMIN")
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
