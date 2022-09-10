package com.example.twitterapi.controller;

import com.example.twitterapi.dto.SigninForm;
import com.example.twitterapi.dto.SignupForm;
import com.example.twitterapi.dto.TokenDTO;
import com.example.twitterapi.dto.UserDTO;
import com.example.twitterapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody SignupForm signupForm){
        return new ResponseEntity<>(authService.signup(signupForm), HttpStatus.CREATED);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<TokenDTO> signin(@Valid @RequestBody SigninForm signinForm){
        return ResponseEntity.ok(authService.signin(signinForm));
    }

    @GetMapping
    public  ResponseEntity<UserDTO> getMyinfo() {
        return ResponseEntity.ok(authService.getMyInfo());
    }
}
