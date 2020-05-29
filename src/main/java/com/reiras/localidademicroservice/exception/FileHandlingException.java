package com.reiras.localidademicroservice.exception;

public class FileHandlingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileHandlingException(String msg) {
		super(msg);
	}

	public FileHandlingException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
