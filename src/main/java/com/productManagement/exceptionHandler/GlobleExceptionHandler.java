package com.productManagement.exceptionHandler;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<?> productNotFoundException(ProductNotFoundException e){
		
		return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body(e.getMessage());
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
		
		HashMap<String, String> errors = new HashMap<>();
		
		e.getFieldErrors()
						.forEach( ex -> 
								errors.put(ex.getField(), ex.getDefaultMessage())
								);
		
		return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body(errors);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptions(Exception e){
		return ResponseEntity.badRequest().body("Something went wrong "+e.getClass()+" : "+e.getLocalizedMessage());
	}

}
