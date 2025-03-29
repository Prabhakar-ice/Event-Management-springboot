package com.prabha.Event_Management_System;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prabha.Event_Management_System.Entities.DuplicationResourceException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	 @Override // To handle method argument/Object validating 
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(
				MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		 Map<String,Object> map = new LinkedHashMap<>();
		 
		 map.put("TimeStramps", new Date());
		 map.put("Status", status.value());
		 
		 List<String> error = ex.getBindingResult()
				 				.getFieldErrors()
				 				.stream()
				 				.map((x)-> x.getDefaultMessage())
				 				.collect(Collectors.toList());
		 map.put("errors", error);
		 
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	// To handle Http Unsupported Method Exception
	 protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			 HttpRequestMethodNotSupportedException ex,HttpStatusCode status, HttpHeaders headers){
		 
		 String error =String.format("This '%s' is not allowed, supported method is : %s",
				 		ex.getMethod()
				 		,ex.getSupportedHttpMethods());
		 		
		 return new ResponseEntity<>(error,HttpStatus.METHOD_NOT_ALLOWED);
	 }
	 // To handle path Variable Exception
	 protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
			 				HttpHeaders headers,HttpStatusCode status){
		 
		 
		 return new ResponseEntity<>(HttpStatus.CONFLICT);
	 }
	 @ExceptionHandler(DuplicationResourceException.class)
	 protected ResponseEntity<Object> handleDuplicationResource(DuplicationResourceException ex){
		 
		 String error = ex.getMessage();
		 return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	 }
}

