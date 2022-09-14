package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Like;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like,Long> {
    Long countByUser(AppUser user);
    Long countByTweet(Tweet tweet);

}
