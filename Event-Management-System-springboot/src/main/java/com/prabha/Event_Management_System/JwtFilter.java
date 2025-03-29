package com.prabha.Event_Management_System;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prabha.Event_Management_System.Service.JwtService;
import com.prabha.Event_Management_System.Service.UserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		
		if(authHeader !=null && authHeader.startsWith("Bearer ")) {
			
			
			token=authHeader.substring(7);
			
			username=jwtService.extractUsername(token);
		}
		
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
				UserDetails userDetails= new User(username,"",Collections.emptyList());
			
				System.out.println("filter 1");
			if(jwtService.isTokenValid(token, username)) {
				System.out.println("filter 2");
				
				UsernamePasswordAuthenticationToken userAuth=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				System.out.println("filter 3");
				
				userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				System.out.println("filter 4");
				SecurityContextHolder.getContext().setAuthentication(userAuth);
			}
			
		}
		System.out.println("filter 5");
		filterChain.doFilter(request, response);
	}

}
