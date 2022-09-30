package com.weatheralert.exception;

public class IllegalReturnValueException extends RuntimeException {

	private static final long serialVersionUID = -2196142815268143531L;

	public IllegalReturnValueException() {
		super();
	}

	public IllegalReturnValueException(String message) {
		super(message);
	}

}
