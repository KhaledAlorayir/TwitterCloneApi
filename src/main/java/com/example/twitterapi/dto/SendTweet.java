package com.example.twitterapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SendTweet {

    @NotBlank(message = "tweet can't be empty!")
    @Size(max = 280,message = "max length is 280 chars!")
    private String content;

    public void setContent(String content) {
        this.content = content.trim();
    }
}
