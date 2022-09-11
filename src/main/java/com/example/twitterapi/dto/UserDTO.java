package com.example.twitterapi.dto;

import com.example.twitterapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Date created_at;
    private String bio;
    private String img_url;
    private Long role_id;
    private Long followers_count;
    private Long following_count;
    private Long tweets_count;
    private Long likes_count;


}
