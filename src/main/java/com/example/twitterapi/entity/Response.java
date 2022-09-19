package com.example.twitterapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Response {

    public Response(Tweet original, Tweet replay) {
        this.original = original;
        this.replay = replay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tweet_id")
    private Tweet original;
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "replay_id")
    private Tweet replay;
}
