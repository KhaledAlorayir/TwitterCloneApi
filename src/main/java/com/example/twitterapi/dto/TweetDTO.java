package com.example.twitterapi.dto;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class TweetDTO {
    private Long id;
    private String content;
    private UserListDTO owner;
    private Date created_at;
    private long likes_count;
    private long responses_count;
}
