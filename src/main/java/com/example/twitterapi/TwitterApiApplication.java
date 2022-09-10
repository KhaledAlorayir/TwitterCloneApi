package com.example.twitterapi;

import com.example.twitterapi.repo.FollowsRepo;
import com.example.twitterapi.repo.RoleRepo;
import com.example.twitterapi.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TwitterApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}



}
