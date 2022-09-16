package com.example.twitterapi.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "follower_id")
    private AppUser follower;
    @ManyToOne(optional = false)
    @JoinColumn(name = "followed_id")
    private AppUser followed;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
}
