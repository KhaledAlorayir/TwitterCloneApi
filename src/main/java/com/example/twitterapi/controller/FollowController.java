package com.example.twitterapi.controller;

import com.example.twitterapi.dto.Message;
import com.example.twitterapi.dto.Pagination;
import com.example.twitterapi.dto.UserListDTO;
import com.example.twitterapi.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<Message> Follow(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(followService.follow(id),HttpStatus.CREATED);
    }

    @GetMapping(path = "/followers")
    public ResponseEntity<Pagination<UserListDTO>> getMyFollowers(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(followService.getMyFollowers(page));
    }

    @GetMapping(path = "/following")
    public ResponseEntity<Pagination<UserListDTO>> getMyFollowing(@RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(followService.getMyFollowing(page));
    }

}
