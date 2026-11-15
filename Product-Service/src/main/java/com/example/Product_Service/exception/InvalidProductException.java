package com.example.Product_Service.exception;

// Domain exception reserved for product rules handled by the service layer.
public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}
