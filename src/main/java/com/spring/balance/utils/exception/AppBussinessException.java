package com.spring.balance.utils.exception;

public class AppBussinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AppBussinessException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AppBussinessException(String message) {
		super(message);
		
	}

	
}
