package com.example.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadCredException extends RuntimeException {
    public BadCredException() {
        super("invalid credentials");
    }
}
