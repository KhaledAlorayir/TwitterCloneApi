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
    public ResponseEntity<Pagination<TweetDTO>> getMyTweets(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(tweetService.getMyTweets(page));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TweetDTO> getTweetById(@PathVariable long id){
        return ResponseEntity.ok(tweetService.getTweetById(id));
    }

    @GetMapping(path = "/{id}/user")
    public ResponseEntity<Pagination<TweetDTO>> getUserTweets(@PathVariable long id,@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(tweetService.getUserTweets(id,page));
    }

    @GetMapping(path = "/timeline")
    public ResponseEntity<Pagination<TweetDTO>> getTimeline(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(tweetService.getTimeline(page));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<TweetDTO> replayToTweet(@PathVariable long id, @Valid @RequestBody SendTweet sendTweet){
        return new ResponseEntity<>(tweetService.ReplayToTweet(sendTweet,id),HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}/replies")
    public ResponseEntity<Pagination<TweetDTO>> getTweetReplies(@PathVariable long id,@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(tweetService.getTweetReplies(id,page));
    }

    @GetMapping(path = "/{id}/original")
    public ResponseEntity<TweetDTO> getOriginalTweet(@PathVariable long id){
        return ResponseEntity.ok(tweetService.getOriginalTweet(id));
    }

}
