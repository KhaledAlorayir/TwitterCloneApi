package com.example.twitterapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Tweet {
    public Tweet(String content, AppUser owner, boolean replay) {
        this.content = content;
        this.owner = owner;
        this.replay = replay;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 280)
    private String content;
    @Column(nullable = false)
    private boolean replay;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private AppUser owner;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "tweet")
    private List<Like> likes;
    @OneToMany(mappedBy = "original", cascade = CascadeType.REMOVE)
    private List<Response> responses;
}
