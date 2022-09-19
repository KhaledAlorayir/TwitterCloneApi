package com.example.twitterapi.controller;

import com.example.twitterapi.dto.*;
import com.example.twitterapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping(path = "/username")
    public ResponseEntity<UserDTO> changeUsername(@Valid @RequestBody UsernameDTO usernameDTO) {
        return ResponseEntity.ok(userService.changeUsername(usernameDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<Pagination<UserListDTO>> searchUsers(@RequestParam String q, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(userService.searchUsers(q,page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserByid(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(userService.getUserbyID(id));
    }

    @PatchMapping(path = "/bio")
    public ResponseEntity<UserDTO> changeBio(@Valid @RequestBody BioDTO bioDTO){
        return ResponseEntity.ok(userService.changBio(bioDTO));
    }

    @PatchMapping(path = "/imgurl")
    public ResponseEntity<UserDTO> changeImgViaUrl(@Valid @RequestBody ImgUrl imgUrl){
        return ResponseEntity.ok(userService.changeImgViaURL(imgUrl));
    }

    @PatchMapping(path = "/imgfile")
    public ResponseEntity<UserDTO> changeImgViaFile(@RequestParam("image") MultipartFile file){
        return ResponseEntity.ok(userService.changeImgViaFile(file));
    }

    @GetMapping(path = "/img/{filename}")
    public ResponseEntity<byte[]> getImg(@PathVariable String filename){
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(userService.getImg(filename));
    }



}
