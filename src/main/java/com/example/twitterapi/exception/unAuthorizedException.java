package com.example.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class unAuthorizedException extends RuntimeException {
public unAuthorizedException() {
    super("Unauthorized");
}
}
