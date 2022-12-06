package com.main.neosoft.exceptionhandling;

public class MobileNumberFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MobileNumberFormatException(String msg) {
		super(msg);
	}
}
