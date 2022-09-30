package com.weatheralert.exception;

public class CommandConflictException extends RuntimeException {

	private static final long serialVersionUID = -2196142857268143531L;

	public CommandConflictException() {
		super();
	}

	public CommandConflictException(String message) {
		super(message);
	}

}
