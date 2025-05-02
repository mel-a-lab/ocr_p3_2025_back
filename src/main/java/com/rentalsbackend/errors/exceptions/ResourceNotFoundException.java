package com.rentalsbackend.errors.exceptions;

public class ResourceNotFoundException extends NotFoundException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
