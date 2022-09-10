package com.example.twitterapi;

import com.example.twitterapi.Repo.FollowsRepo;
import com.example.twitterapi.Repo.RoleRepo;
import com.example.twitterapi.Repo.UserRepo;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Follows;
import com.example.twitterapi.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
public class TwitterApiApplication {

	private final RoleRepo roleRepo;
	private final UserRepo userRepo;
	private final FollowsRepo followsRepo;

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}



}
