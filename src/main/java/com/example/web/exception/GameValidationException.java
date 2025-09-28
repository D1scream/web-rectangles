package com.example.web.exception;

public class GameValidationException extends RuntimeException {
    public GameValidationException(String message) {
        super(message);
    }
}
