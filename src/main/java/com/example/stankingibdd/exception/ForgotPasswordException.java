package com.example.stankingibdd.exception;

public class ForgotPasswordException extends RuntimeException {
    public ForgotPasswordException(String message) {
        super(message);
    }
}