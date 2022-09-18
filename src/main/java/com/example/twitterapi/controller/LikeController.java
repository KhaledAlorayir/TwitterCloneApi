package com.example.twitterapi.controller;

import com.example.twitterapi.dto.Message;
import com.example.twitterapi.dto.Pagination;
import com.example.twitterapi.dto.TweetDTO;
import com.example.twitterapi.dto.UserListDTO;
import com.example.twitterapi.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<Message> LikeTweet(@PathVariable long id){
        return new ResponseEntity<>(likeService.LikeTweet(id), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pagination<UserListDTO>> getTweetLikes(@PathVariable long id, @RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(likeService.getTweetLikes(id,page));
    }

    @GetMapping
    public ResponseEntity<Pagination<TweetDTO>> getMyLikes(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(likeService.getMyLikes(page));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<Pagination<TweetDTO>> getUserLikes(@PathVariable long id, @RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(likeService.getUserLikes(id,page));
    }
}
