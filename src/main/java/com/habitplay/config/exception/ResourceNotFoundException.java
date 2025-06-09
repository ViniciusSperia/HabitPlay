package com.habitplay.config.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(UUID id) {
        super("Resource not found with ID: " + id);
    }
}
