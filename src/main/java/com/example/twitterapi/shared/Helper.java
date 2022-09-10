package com.example.twitterapi.shared;

import org.springframework.security.core.context.SecurityContextHolder;

public class Helper {
    public static Long getAuth() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
