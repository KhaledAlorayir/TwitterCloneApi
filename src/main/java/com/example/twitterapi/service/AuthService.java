package com.example.twitterapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.twitterapi.dto.SigninForm;
import com.example.twitterapi.dto.SignupForm;
import com.example.twitterapi.dto.TokenDTO;
import com.example.twitterapi.dto.UserDTO;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Role;
import com.example.twitterapi.exception.BadCredException;
import com.example.twitterapi.exception.ExistsException;
import com.example.twitterapi.repo.*;
import com.example.twitterapi.shared.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserService userService;

    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt_secret}")
    private String secret;


    public UserDTO signup(SignupForm signupForm) {
        userRepo.findByEmail(signupForm.getEmail()).ifPresent((user) -> {
            throw new ExistsException("email");
        });

        userRepo.findByUsername(signupForm.getUsername()).ifPresent(user -> {
            throw new ExistsException("username");

        });
        Role user_role = roleRepo.getReferenceById((long)1);
        AppUser user = new AppUser(signupForm.getUsername(),signupForm.getEmail(),encoder.encode(signupForm.getPassword()),user_role);
        userRepo.save(user);
        return new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getCreated_at(),user.getRole().getId(),(long)0,(long)0,(long)0,(long)0);
    }

    public TokenDTO signin(SigninForm signinForm) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinForm.getEmail(),signinForm.getPassword()));

            AppUser user = userRepo.findByEmail(signinForm.getEmail()).get();
            Map<String,String> token_payload = new HashMap<>();
            token_payload.put("email", user.getEmail());
            token_payload.put("uid",user.getId().toString());

            String token = JWT.create()
                    .withPayload(token_payload)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000))
                    .sign(Algorithm.HMAC256(secret));

            return new TokenDTO(token);
        }catch (AuthenticationException e) {
            throw new BadCredException();
        }

    }

    public UserDTO getMyInfo() {
        AppUser user = userRepo.findById(Helper.getAuth()).get();
        return userService.getUserDTO(user);
    }




}
