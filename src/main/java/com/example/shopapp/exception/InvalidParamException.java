package com.example.shopapp.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String message) {
        super(message);
    }
}
