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
    private Long role_id;

}
