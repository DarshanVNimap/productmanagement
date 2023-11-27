package com.productManagement.exceptionHandler;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException(String message) {
		super(message);
	}
}
