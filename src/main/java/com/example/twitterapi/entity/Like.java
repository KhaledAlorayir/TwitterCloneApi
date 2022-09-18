package com.example.twitterapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tweet_likes")
@Data
@NoArgsConstructor

public class Like {

    public Like(AppUser user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

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
    @Column(name = "created_at")
    private Date createdAt;
}
