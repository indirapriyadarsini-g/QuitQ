package com.quitq.ECom.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
@Component // registers jwt utility with spring
public class JwtUtil {
	private String SECRET_KEY = "secret";
	
	// this method is used to extract username from the token that comes from the ui
	// this happens automatically with springsecurity httpBasic
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	
	
	
	// this method will set the expiry date of the token (usually 3 days) = 3 x 24 x 60 x 60
	public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
	
	// this method resolves a claim placed by UI developer while giving the token
	// this claim must also be registered with overall claims class
	//*PayLoad part conatins the actual data to be trasfereed using  token/
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // extract the claims basis given token
		final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	// this method 1st encodes the secret key using base64 encoder and parses it to create a stronger token
	// such methods are called helper methods
	private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes())).parseClaimsJws(token).getBody();
    }
	
	private String createToken(Map<String, Object> claims, String subject) {
		 
		// this is called builder design pattern which we r using to build jwts object
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*10*24*60*60*1000))
                 .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .compact();
    }
	
	// this will generate the token basis username
	public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
 
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	// to validate the token when it comes from the UI
	public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
