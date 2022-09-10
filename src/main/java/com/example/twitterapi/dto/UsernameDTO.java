package com.example.twitterapi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UsernameDTO {
    @NotBlank(message = "username is required")
    @Length(max = 25, message = "the maximum length of the username is 25")
    private String username;

    public void setUsername(String username) {
        this.username = username.trim();
    }
}
