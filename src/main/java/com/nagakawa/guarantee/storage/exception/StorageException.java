package com.nagakawa.guarantee.storage.exception;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 5473111695265725278L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
