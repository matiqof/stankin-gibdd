package com.example.stankingibdd.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginException extends UsernameNotFoundException {

    public LoginException(String msg) {
        super(msg);
    }
}
