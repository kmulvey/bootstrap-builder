package com.ss.exceptions;

public class MissingArgumentException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingArgumentException(String msg) {
		super(msg);
	}
}