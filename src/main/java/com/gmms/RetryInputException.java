package com.gmms;

// custom exception
public class RetryInputException extends RuntimeException {
    public RetryInputException(String message) {
        super(message);
    }
}