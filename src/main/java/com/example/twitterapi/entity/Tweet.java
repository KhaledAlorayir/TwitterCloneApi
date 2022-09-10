package com.example.twitterapi.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Tweet {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 280)
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private AppUser owner;
    @CreationTimestamp
    private Date created_at;
    @OneToMany(mappedBy = "tweet")
    private List<Like> likes;
    @OneToMany(mappedBy = "replay")
    private List<Response> responses;
}
