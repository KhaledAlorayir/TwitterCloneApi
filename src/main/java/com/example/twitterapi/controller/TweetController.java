package com.example.twitterapi.controller;

import com.example.twitterapi.dto.*;
import com.example.twitterapi.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tweet")
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetDTO> Tweet(@Valid @RequestBody SendTweet sendTweet){
        return new ResponseEntity<>(tweetService.Tweet(sendTweet), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Message> deleteTweet(@PathVariable long id){
        return ResponseEntity.ok(tweetService.deleteTweet(id));
    }

    @GetMapping
    public ResponseEntity<Pagination2<TweetDTO>> getMyTweets(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(tweetService.getMyTweets(page));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TweetDTO> getTweetById(@PathVariable long id){
        return ResponseEntity.ok(tweetService.getTweetById(id));
    }

}
