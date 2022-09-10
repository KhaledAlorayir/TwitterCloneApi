package com.example.twitterapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tweet_id")
    private Tweet original;
    @ManyToOne(optional = false)
    @JoinColumn(name = "replay_id")
    private Tweet replay;
}
