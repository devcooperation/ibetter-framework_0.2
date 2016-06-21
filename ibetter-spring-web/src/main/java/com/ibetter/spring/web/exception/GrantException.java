package com.ibetter.spring.web.exception;

public class GrantException extends RuntimeException {

	public GrantException() {
		super();
	}

	public GrantException(String message, Throwable cause) {
		super(message, cause);
	}

	public GrantException(String message) {
		super(message);
	}

	public GrantException(Throwable cause) {
		super(cause);
	}

}
