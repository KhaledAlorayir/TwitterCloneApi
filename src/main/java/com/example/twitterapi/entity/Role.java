package com.example.twitterapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, name = "role_name")
    private String RoleName;
    @OneToMany(mappedBy = "role")
    private List<AppUser> users;

}
