package com.prabha.Event_Management_System.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.prabha.Event_Management_System.Entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService{

	
	public String generateToken(User userDetail) {
		Map<String,Object> extraClaim=new HashMap<>();
		System.out.println("login 5");
		extraClaim.put("id", userDetail.getId());
		extraClaim.put("email", userDetail.getEmail());
		
		return Jwts.builder().claims(extraClaim)
				.subject(userDetail.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*5))
				.signWith(getKey())
				.compact();
				
	}
	
	public String generateRefreshToken(User userDetail) {
		
		Map<String,Object> extraClaim=new HashMap<>();
		System.out.println("login 6");
		extraClaim.put("id", userDetail.getId());
		extraClaim.put("email", userDetail.getEmail());
		
		return Jwts.builder().claims(extraClaim)
				.subject(userDetail.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
				.signWith(getKey())
				.compact();
				
	}
	private Key getKey() {
		byte[] key=Decoders.BASE64.decode("polikmnjuyhb123654tgvcfredx987456edxzswqaz12369852147poiuytrewq");
		return Keys.hmacShaKeyFor(key);
	}
	
	private Claims extractAllClaims(String token) {

		return Jwts.parser()
				.verifyWith((SecretKey) getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T extractClaims(String token,Function<Claims,T> claimResolver) {

		final Claims claim=extractAllClaims(token);
		 return claimResolver.apply(claim);
	}
	
	public String extractUsername(String token) {

		return extractClaims(token,Claims::getSubject);
	}
	
	public int extractId(String token) {
		return extractClaims(token,t ->t.get("id", Integer.class) );
	}
	
	public boolean isTokenValid(String token,String username) {
		
		return (extractUsername(token).equals(username) && ! isTokenExpired(token) );
	}
	
	public boolean isTokenExpired(String token) {
		return extractTokenExpiration(token).before(new Date());
	}
	
	public Date extractTokenExpiration(String token) {
		return extractClaims(token,Claims::getExpiration);
	}
}
