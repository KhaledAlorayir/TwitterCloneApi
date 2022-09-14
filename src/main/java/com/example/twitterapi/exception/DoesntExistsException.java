package com.example.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoesntExistsException extends RuntimeException {
    public DoesntExistsException(){
        super("resource doesn't exist");
    }
}
