package com.example.twitterapi.controller;

import com.example.twitterapi.dto.Pagination;
import com.example.twitterapi.dto.UserDTO;
import com.example.twitterapi.dto.UserListDTO;
import com.example.twitterapi.dto.UsernameDTO;
import com.example.twitterapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
