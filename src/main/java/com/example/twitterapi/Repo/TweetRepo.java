package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepo extends JpaRepository<Tweet,Long> {
}
