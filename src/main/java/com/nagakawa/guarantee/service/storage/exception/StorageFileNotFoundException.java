package com.nagakawa.guarantee.service.storage.exception;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -3029673002512876906L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
