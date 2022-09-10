package com.example.twitterapi.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tweet_likes")
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;
    @CreationTimestamp
    private Date created_at;
}
